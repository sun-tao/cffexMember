package com.example.cffexmember.dto;

import lombok.Data;

/**
 * 任务列表项DTO
 */
@Data
public class TaskListItem {
    private String applicationId;
    private String memberName;
    private String applicantUserName;
    private String createTime;
    
    public TaskListItem() {}
    
    public TaskListItem(String applicationId, String memberName, String applicantUserName,String createTime) {
        this.applicationId = applicationId;
        this.memberName = memberName;
        this.applicantUserName = applicantUserName;
        this.createTime = createTime;
    }
} 