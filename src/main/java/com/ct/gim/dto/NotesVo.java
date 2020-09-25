package com.ct.gim.dto;

import java.util.List;

import lombok.Data;

@Data
public class NotesVo {
    private List<MilestoneVo> milestoneVos;
    private List<IssueVo> allIssues;
}
