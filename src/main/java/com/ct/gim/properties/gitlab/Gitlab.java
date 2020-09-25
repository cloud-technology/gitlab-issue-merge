package com.ct.gim.properties.gitlab;

import java.util.Collections;
import java.util.List;

import lombok.Data;

@Data
public class Gitlab {
    private String accessToken;
    private String name;
    private Integer projectId;
    private String milestoneTitle;
    private Integer milestoneIid;
    private String pathToGenerateFile;
    // 平台發佈用專案
	private List<Integer> mergeProjectId = Collections.emptyList();
}