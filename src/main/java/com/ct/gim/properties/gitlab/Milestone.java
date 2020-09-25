package com.ct.gim.properties.gitlab;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "iid", "project_id", "title", "description", "state", "created_at", "updated_at", "due_date",
        "start_date", "expired", "web_url" })
public class Milestone {
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
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("due_date")
    private Object dueDate;
    @JsonProperty("start_date")
    private Object startDate;
    @JsonProperty("expired")
    private Object expired;
    @JsonProperty("web_url")
    private String webUrl;
}