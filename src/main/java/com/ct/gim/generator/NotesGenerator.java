package com.ct.gim.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ct.gim.clients.GitlabClient;
import com.ct.gim.dto.IssueVo;
import com.ct.gim.dto.MilestoneVo;
import com.ct.gim.dto.NotesVo;
import com.ct.gim.dto.SectionVo;
import com.ct.gim.properties.gitlab.Gitlab;
import com.ct.gim.properties.gitlab.Milestone;
import com.ct.gim.properties.gitlab.issues.Issue;
import com.samskivert.mustache.Mustache;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotesGenerator {

    @Value("classpath:Notes.mustache")
    private Resource resourceNotes;

    private final Mustache.Compiler compiler;

    @NonNull
    private final GitlabClient gitlabClient;

    public void generate(final Gitlab gitlab) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        NotesVo notesVo = new NotesVo();
        String authorization = String.format("Bearer %s", gitlab.getAccessToken());
        byte[] bdata = FileCopyUtils.copyToByteArray(resourceNotes.getInputStream());
        String noteTemplate = new String(bdata, StandardCharsets.UTF_8);
        // 先取出所有 Milestone
        List<Milestone> milestones = gitlabClient.getMilestones(authorization, gitlab.getProjectId());
        // 拿出未關閉的
        milestones = milestones.stream().filter(milestone -> "active".equals(milestone.getState()))
                .collect(Collectors.toList());
        List<MilestoneVo> milestoneVos = new ArrayList();
        for (Milestone milestone : milestones) {
            MilestoneVo milestoneVo = modelMapper.map(milestone, MilestoneVo.class);
            List<Issue> issues = gitlabClient.getIssuesByMilestone(authorization, gitlab.getProjectId(),
                    milestone.getId());
            issues = issues.stream().filter(issue -> "closed".equals(issue.getState())).collect(Collectors.toList());
            List<IssueVo> IssueVos = this.issueConverterToIssueVo(issues);
            SectionVo sectionVo = new SectionVo();
            for (IssueVo issueVo : IssueVos) {
                sectionVo.addIssue(issueVo);
            }
            milestoneVo.setSectionVo(sectionVo);
            milestoneVos.add(milestoneVo);
        }
        notesVo.setMilestoneVos(milestoneVos);

        final List<IssueVo> allIssues = this.getMergeProjectIssues(authorization, gitlab.getMergeProjectId());
        notesVo.setAllIssues(allIssues);

        // log.debug("noteTemplate={}", noteTemplate);

        // System.out.println(compiler.compile(noteTemplate).execute(notesVo));
        this.writeContentToFile(compiler.compile(noteTemplate).execute(notesVo), gitlab.getPathToGenerateFile());

    }

    private void writeContentToFile(final String content, final String path) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        FileCopyUtils.copy(content, new FileWriter(new File(path)));
    }

    private List<IssueVo> getMergeProjectIssues(final String authorization, final List<Integer> mergeProjectIds) {
        List<Issue> allIssues = new ArrayList<Issue>();
        for (Integer projectId : mergeProjectIds) {
            List<Issue> issues = gitlabClient.getIssuesByProject(authorization, projectId);
            allIssues.addAll(issues);
        }
        allIssues = allIssues.stream().filter(issue -> "closed".equals(issue.getState())).collect(Collectors.toList());
        return this.issueConverterToIssueVo(allIssues);
    }

    private List<IssueVo> issueConverterToIssueVo(List<Issue> issues) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(this.getDateTimeConverter());
        List<IssueVo> issueVos = modelMapper.map(issues, new TypeToken<List<IssueVo>>() {
        }.getType());
        return issueVos;
    }

    private Converter<ZonedDateTime, String> getDateTimeConverter() {
        Converter<ZonedDateTime, String> dateTimeConverter = new Converter<ZonedDateTime, String>() {
            private String pattern = "MM/dd HH:mm:ss";
            private String zoneIdName = "Asia/Taipei";
            private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            private ZoneId zoneId = ZoneId.of(zoneIdName);

            @Override
            public String convert(final MappingContext<ZonedDateTime, String> context) {
                return context.getSource().withZoneSameInstant(zoneId).format(formatter);
            }
        };
        return dateTimeConverter;
    }
}
