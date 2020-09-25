package com.ct.gim.properties.gitlab.release;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "self", "edit_url" })
@Data
/**
 * 資源連結
 */
public class Links {
    @JsonProperty("self")
    private String self;
    @JsonProperty("edit_url")
    private String editUrl;
}
