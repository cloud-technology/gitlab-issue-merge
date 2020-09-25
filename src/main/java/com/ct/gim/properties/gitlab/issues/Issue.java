package com.ct.gim.properties.gitlab.issues;

import java.time.ZonedDateTime;
import java.util.List;

import com.ct.gim.properties.gitlab.Milestone;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "iid", "project_id", "title", "description", "state", "created_at", "updated_at",
        "closed_at", "closed_by", "labels", "milestone", "assignees", "author", "assignee", "user_notes_count",
        "merge_requests_count", "upvotes", "downvotes", "due_date", "confidential", "discussion_locked", "web_url",
        "time_stats", "task_completion_status", "blocking_issues_count" })
public class Issue {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("iid")
    private Integer iid;
    @JsonProperty("project_id")
    private Integer projectId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("state")
    private String state;
    @JsonProperty("created_at")
    private ZonedDateTime createdAt;
    @JsonProperty("updated_at")
    private ZonedDateTime updatedAt;
    @JsonProperty("closed_at")
    private ZonedDateTime closedAt;
    @JsonProperty("closed_by")
    private User closedBy;
    @JsonProperty("labels")
    private List<String> labels = null;
    @JsonProperty("milestone")
    private Milestone milestone;
    @JsonProperty("assignees")
    private List<User> assignees = null;
    @JsonProperty("author")
    private User author;
    @JsonProperty("assignee")
    private User assignee;
    @JsonProperty("user_notes_count")
    private Integer userNotesCount;
    @JsonProperty("merge_requests_count")
    private Integer mergeRequestsCount;
    @JsonProperty("upvotes")
    private Integer upvotes;
    @JsonProperty("downvotes")
    private Integer downvotes;
    @JsonProperty("due_date")
    private Object dueDate;
    @JsonProperty("confidential")
    private Boolean confidential;
    @JsonProperty("discussion_locked")
    private Object discussionLocked;
    @JsonProperty("web_url")
    private String webUrl;
    @JsonProperty("time_stats")
    private TimeStats timeStats;
    @JsonProperty("task_completion_status")
    private TaskCompletionStatus taskCompletionStatus;
    @JsonProperty("blocking_issues_count")
    private Object blockingIssuesCount;
}