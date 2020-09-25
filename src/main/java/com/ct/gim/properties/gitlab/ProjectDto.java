package com.ct.gim.properties.gitlab;

import java.security.Permissions;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "description", "name", "name_with_namespace", "path", "path_with_namespace", "created_at",
        "default_branch", "tag_list", "ssh_url_to_repo", "http_url_to_repo", "web_url", "readme_url", "avatar_url",
        "forks_count", "star_count", "last_activity_at", "namespace", "_links", "packages_enabled", "empty_repo",
        "archived", "visibility", "resolve_outdated_diff_discussions", "container_registry_enabled",
        "container_expiration_policy", "issues_enabled", "merge_requests_enabled", "wiki_enabled", "jobs_enabled",
        "snippets_enabled", "service_desk_enabled", "service_desk_address", "can_create_merge_request_in",
        "issues_access_level", "repository_access_level", "merge_requests_access_level", "forking_access_level",
        "wiki_access_level", "builds_access_level", "snippets_access_level", "pages_access_level", "emails_disabled",
        "shared_runners_enabled", "lfs_enabled", "creator_id", "import_status", "import_error", "open_issues_count",
        "runners_token", "ci_default_git_depth", "public_jobs", "build_git_strategy", "build_timeout",
        "auto_cancel_pending_pipelines", "build_coverage_regex", "ci_config_path", "shared_with_groups",
        "only_allow_merge_if_pipeline_succeeds", "allow_merge_on_skipped_pipeline", "request_access_enabled",
        "only_allow_merge_if_all_discussions_are_resolved", "remove_source_branch_after_merge",
        "printing_merge_request_link_enabled", "merge_method", "suggestion_commit_message", "auto_devops_enabled",
        "auto_devops_deploy_strategy", "autoclose_referenced_issues", "external_authorization_classification_label",
        "compliance_frameworks", "permissions" })
@Data
public class ProjectDto {
    @JsonProperty("id")
    public Integer id;
    @JsonProperty("description")
    public String description;
    @JsonProperty("name")
    public String name;
    @JsonProperty("name_with_namespace")
    public String nameWithNamespace;
    @JsonProperty("path")
    public String path;
    @JsonProperty("path_with_namespace")
    public String pathWithNamespace;
    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("default_branch")
    public String defaultBranch;
    @JsonProperty("tag_list")
    public List<Object> tagList = null;
    @JsonProperty("ssh_url_to_repo")
    public String sshUrlToRepo;
    @JsonProperty("http_url_to_repo")
    public String httpUrlToRepo;
    @JsonProperty("web_url")
    public String webUrl;
    @JsonProperty("readme_url")
    public String readmeUrl;
    @JsonProperty("forks_count")
    public Integer forksCount;
    @JsonProperty("star_count")
    public Integer starCount;
    @JsonProperty("last_activity_at")
    public String lastActivityAt;
    @JsonProperty("packages_enabled")
    public Boolean packagesEnabled;
    @JsonProperty("empty_repo")
    public Boolean emptyRepo;
    @JsonProperty("archived")
    public Boolean archived;
    @JsonProperty("visibility")
    public String visibility;
    @JsonProperty("resolve_outdated_diff_discussions")
    public Boolean resolveOutdatedDiffDiscussions;
    @JsonProperty("container_registry_enabled")
    public Boolean containerRegistryEnabled;
    @JsonProperty("issues_enabled")
    public Boolean issuesEnabled;
    @JsonProperty("merge_requests_enabled")
    public Boolean mergeRequestsEnabled;
    @JsonProperty("wiki_enabled")
    public Boolean wikiEnabled;
    @JsonProperty("jobs_enabled")
    public Boolean jobsEnabled;
    @JsonProperty("snippets_enabled")
    public Boolean snippetsEnabled;
    @JsonProperty("service_desk_enabled")
    public Boolean serviceDeskEnabled;
    @JsonProperty("service_desk_address")
    public String serviceDeskAddress;
    @JsonProperty("can_create_merge_request_in")
    public Boolean canCreateMergeRequestIn;
    @JsonProperty("issues_access_level")
    public String issuesAccessLevel;
    @JsonProperty("repository_access_level")
    public String repositoryAccessLevel;
    @JsonProperty("merge_requests_access_level")
    public String mergeRequestsAccessLevel;
    @JsonProperty("forking_access_level")
    public String forkingAccessLevel;
    @JsonProperty("wiki_access_level")
    public String wikiAccessLevel;
    @JsonProperty("builds_access_level")
    public String buildsAccessLevel;
    @JsonProperty("snippets_access_level")
    public String snippetsAccessLevel;
    @JsonProperty("pages_access_level")
    public String pagesAccessLevel;
    @JsonProperty("shared_runners_enabled")
    public Boolean sharedRunnersEnabled;
    @JsonProperty("lfs_enabled")
    public Boolean lfsEnabled;
    @JsonProperty("creator_id")
    public Integer creatorId;
    @JsonProperty("import_status")
    public String importStatus;
    @JsonProperty("open_issues_count")
    public Integer openIssuesCount;
    @JsonProperty("runners_token")
    public String runnersToken;
    @JsonProperty("ci_default_git_depth")
    public Integer ciDefaultGitDepth;
    @JsonProperty("public_jobs")
    public Boolean publicJobs;
    @JsonProperty("build_git_strategy")
    public String buildGitStrategy;
    @JsonProperty("build_timeout")
    public Integer buildTimeout;
    @JsonProperty("auto_cancel_pending_pipelines")
    public String autoCancelPendingPipelines;
    @JsonProperty("build_coverage_regex")
    public String buildCoverageRegex;
    @JsonProperty("ci_config_path")
    public String ciConfigPath;
    @JsonProperty("only_allow_merge_if_pipeline_succeeds")
    public Boolean onlyAllowMergeIfPipelineSucceeds;
    @JsonProperty("request_access_enabled")
    public Boolean requestAccessEnabled;
    @JsonProperty("only_allow_merge_if_all_discussions_are_resolved")
    public Boolean onlyAllowMergeIfAllDiscussionsAreResolved;
    @JsonProperty("remove_source_branch_after_merge")
    public Boolean removeSourceBranchAfterMerge;
    @JsonProperty("printing_merge_request_link_enabled")
    public Boolean printingMergeRequestLinkEnabled;
    @JsonProperty("merge_method")
    public String mergeMethod;
    @JsonProperty("auto_devops_enabled")
    public Boolean autoDevopsEnabled;
    @JsonProperty("auto_devops_deploy_strategy")
    public String autoDevopsDeployStrategy;
    @JsonProperty("autoclose_referenced_issues")
    public Boolean autocloseReferencedIssues;
    @JsonProperty("external_authorization_classification_label")
    public String externalAuthorizationClassificationLabel;
}
