package com.ct.gim.dto;

import lombok.Data;

@Data
public class MilestoneVo {
    private Integer id;
    private Integer projectId;
    private String title;
    private SectionVo sectionVo;
}
