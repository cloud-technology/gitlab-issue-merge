package com.ct.gim.dto;

import java.util.Collections;
import java.util.List;

import lombok.Data;

@Data
public class IssueVo {
    private Integer id;
    private Integer iid;
    private String title;
    private String state;
    private List<String> labels = Collections.emptyList();
    private String closedAt;
    private String webUrl;
}
