package com.ct.gim.properties.gitlab.release;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "name", "tag_name", "description", "description_html", "created_at", "released_at", "author",
        "commit", "upcoming_release", "milestones", "commit_path", "tag_path", "assets", "evidences", "_links" })
/**
 * Release 建立結果
 */
@Data
public class ReleaseCreateResultDto {
    @JsonProperty("name")
    private String name;
    @JsonProperty("tag_name")
    private String tagName;
    @JsonProperty("description")
    private String description;
    @JsonProperty("description_html")
    private String descriptionHtml;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("released_at")
    private String releasedAt;
    @JsonProperty("_links")
    private Links links;
}
