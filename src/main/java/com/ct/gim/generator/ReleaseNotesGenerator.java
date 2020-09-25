package com.ct.gim.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.ct.gim.clients.GitlabClient;
import com.ct.gim.clients.MilestonesQueryParams;
import com.ct.gim.properties.ApplicationProperties;
import com.ct.gim.properties.gitlab.Gitlab;
import com.ct.gim.properties.gitlab.Milestone;
import com.ct.gim.properties.gitlab.ProjectDto;
import com.ct.gim.properties.gitlab.issues.Issue;
import com.ct.gim.properties.gitlab.issues.IssuesCreateDto;
import com.ct.gim.properties.gitlab.issues.User;
import com.ct.gim.properties.gitlab.milestones.MilestonesCreateDto;
import com.ct.gim.properties.gitlab.release.ReleaseCreateDto;
import com.ct.gim.properties.gitlab.release.ReleaseCreateResultDto;

import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReleaseNotesGenerator {
    private static final String THANK_YOU = "## :heart: Contributors\n\n"
            + "We'd like to thank all the contributors who worked on this release!";
    private static final String milestoneLatest = "latest";
    @NonNull
    private final GitlabClient gitlabClient;
    @NonNull
    private final ApplicationProperties applicationProperties;

    private ReleaseNotesSections sections;

    public void generate(final String milestone, final String path) throws Exception {
        this.sections = new ReleaseNotesSections(applicationProperties);

        final Gitlab gitlab = applicationProperties.getGitlab();
        final Optional<Integer> milestoneNumberOptional = this.getMilestoneNumber(gitlab.getMilestoneTitle());
        if (milestoneNumberOptional.isPresent()) {
            final List<Issue> issues = this.getIssuesForMilestone(milestoneNumberOptional.get(), null, null);
            final String content = generateContent(issues);
            writeContentToFile(content, path);
            final Pattern pattern = Pattern.compile(applicationProperties.getRegexExpression());
            final Matcher matcher = pattern.matcher(milestone);
            if (matcher.matches()) {
                // 符合發佈格式的 Tag 就自動建立 Release
                final ReleaseCreateResultDto releaseCreateResultDto = this.createRelease(content);

            } else {
                throw new IllegalAccessException(
                        String.format("Tag Name %s 不符合 %s", milestone, applicationProperties.getRegexExpression()));
            }
        } else {
            throw new IllegalAccessException(String.format("無法找到對應 Milestone name=%s", milestone));
        }
    }

    private Optional<Integer> getMilestoneNumber(final String milestoneTitle) {
        final Gitlab gitlab = applicationProperties.getGitlab();
        Milestone milestone = null;
        final MilestonesQueryParams params = new MilestonesQueryParams();
        params.setTitle(milestoneTitle);
        final List<Milestone> milestones = gitlabClient
                .getMilestonesByQuery(String.format("Bearer %s", gitlab.getAccessToken()), gitlab.getProjectId(), params);
        if (milestones.size() == 1) {
            milestone = milestones.get(0);
        }
        return Optional.of(milestone.getId());
    }

    public List<Issue> getIssuesForMilestone(final int milestoneNumber, final String organization,
            final String repository) {
        final Gitlab gitlab = applicationProperties.getGitlab();
        return gitlabClient.getIssuesByMilestone(String.format("Bearer %s", gitlab.getAccessToken()),
                gitlab.getProjectId(), milestoneNumber);
    }

    private String generateContent(final List<Issue> issues) {
        final StringBuilder content = new StringBuilder();
        addSectionContent(content, this.sections.collate(issues));
        final Set<User> contributors = getContributors(issues);
        if (!contributors.isEmpty()) {
            addContributorsContent(content, contributors);
        }
        return content.toString();
    }

    private void addSectionContent(final StringBuilder content,
            final Map<ReleaseNotesSection, List<Issue>> sectionIssues) {
        sectionIssues.forEach((section, issues) -> {
            content.append((content.length() != 0) ? "\n" : "");
            content.append("## ").append(section).append("\n\n");
            issues.stream().map(this::getFormattedIssue).forEach(content::append);
        });
    }

    private Set<User> getContributors(final List<Issue> issues) {
        return issues.stream().filter((issue) -> issue.getClosedAt() != null).map(Issue::getClosedBy)
                .collect(Collectors.toSet());
    }

    private void addContributorsContent(final StringBuilder content, final Set<User> contributors) {
        content.append("\n" + THANK_YOU + "\n\n");
        contributors.stream().map(this::formatContributors).forEach(content::append);
    }

    private String formatContributors(final User c) {
        return "- [@" + c.getName() + "]" + "(" + c.getWebUrl() + ")\n";
    }

    private String getFormattedIssue(final Issue issue) {
        final String title = issue.getTitle();
        // title = ghUserMentionPattern.matcher(title).replaceAll("$1`$2`");
        return "- " + title + " " + getLinkToIssue(issue) + " " + this.getFlowInfo(issue) + "\n";
    }

    private String getFlowInfo(final Issue issue) {
        final StringBuffer flow = new StringBuffer();
        for (final String label : issue.getLabels()) {
            if (label.startsWith("flow")) {
                flow.append(flow.length() > 0 ? ", " : "" + label.substring(5));
            }
        }
        if (flow.length() > 0) {
            flow.insert(0, "Flow - ");
            flow.append(" ");
        }
        return flow.toString();
    }

    private String getLinkToIssue(final Issue issue) {
        return "[#" + issue.getIid() + "]" + "(" + issue.getWebUrl() + ")";
    }

    private void writeContentToFile(final String content, final String path) throws IOException {
        FileCopyUtils.copy(content, new FileWriter(new File(path)));
    }

    private ReleaseCreateResultDto createRelease(final String content) {
        final Gitlab gitlab = applicationProperties.getGitlab();
        final ReleaseCreateDto releaseCreateDto = new ReleaseCreateDto();
        releaseCreateDto.setName(gitlab.getMilestoneTitle());
        releaseCreateDto.setTagName(gitlab.getMilestoneTitle());
        releaseCreateDto.setDescription(content);
        releaseCreateDto.setRef(gitlab.getMilestoneTitle());
        releaseCreateDto.setMilestones(Arrays.asList(gitlab.getMilestoneTitle()));
        log.debug("releaseCreateDto={}", releaseCreateDto);
        final ReleaseCreateResultDto releaseCreateResultDto = gitlabClient.createRelease(
                String.format("Bearer %s", gitlab.getAccessToken()), gitlab.getProjectId(), releaseCreateDto);
        log.debug("releaseCreateResultDto={}", releaseCreateResultDto);
        return releaseCreateResultDto;
    }

    
}