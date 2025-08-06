package com.example.cffexmember.dto;

import lombok.Data;

/**
 * 审批历史项DTO
 */
@Data
public class ApprovalHistoryItem {
    private Integer historyId;
    private Integer applicationId;
    private String memberName;
    private String nodeName;
    private String result;
    private String comment;
    private String completedAt;
    
    public ApprovalHistoryItem() {}
    
    public ApprovalHistoryItem(Integer historyId, Integer applicationId, String memberName, 
                             String nodeName, String result, String comment, String completedAt) {
        this.historyId = historyId;
        this.applicationId = applicationId;
        this.memberName = memberName;
        this.nodeName = nodeName;
        this.result = result;
        this.comment = comment;
        this.completedAt = completedAt;
    }
} 