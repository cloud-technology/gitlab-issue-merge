package com.ct.gim.properties.gitlab.issues;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "count", "completed_count" })
public class TaskCompletionStatus {

    @JsonProperty("count")
    private Integer count;
    @JsonProperty("completed_count")
    private Integer completedCount;

}