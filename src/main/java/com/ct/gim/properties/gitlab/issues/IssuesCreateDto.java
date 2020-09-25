package com.ct.gim.properties.gitlab.issues;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class IssuesCreateDto {
    private String title;
    private String description;
    @JsonProperty("milestone_id")
    private Integer milestoneId;
    private String labels;
}
