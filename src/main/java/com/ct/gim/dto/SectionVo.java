package com.ct.gim.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SectionVo {
    private List<IssueVo> newFeatures = new ArrayList<>();
    private List<IssueVo> bugFixes = new ArrayList<>();
    private List<IssueVo> documentation = new ArrayList<>();
    private List<IssueVo> dependencyUpgrades = new ArrayList<>();

    public void addIssue(IssueVo issueVo) {
        if (issueVo.getLabels().contains("enhancement")) {
            this.newFeatures.add(issueVo);
        }
        if (issueVo.getLabels().contains("regression") || issueVo.getLabels().contains("bug")) {
            this.bugFixes.add(issueVo);
        }
        if (issueVo.getLabels().contains("documentation")) {
            this.documentation.add(issueVo);
        }
        if (issueVo.getLabels().contains("dependency-upgrade")) {
            this.dependencyUpgrades.add(issueVo);
        }
    }
}
