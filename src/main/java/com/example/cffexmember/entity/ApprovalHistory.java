package com.example.cffexmember.entity;

import java.util.Date;

/**
 * 审批历史实体类
 */
public class ApprovalHistory {
    private Integer id;
    private Integer applicationId;
    private Integer taskId;
    private Integer operatorId;
    private String operatorName;
    private String operatorGroupCode;
    private String operationType; // APPROVE, REJECT, RETURN, RESUBMIT
    private String comments;
    private Date ctime;

    // 构造函数
    public ApprovalHistory() {}

    public ApprovalHistory(Integer applicationId, Integer taskId, Integer operatorId, 
                         String operatorName, String operatorGroupCode, 
                         String operationType, String comments) {
        this.applicationId = applicationId;
        this.taskId = taskId;
        this.operatorId = operatorId;
        this.operatorName = operatorName;
        this.operatorGroupCode = operatorGroupCode;
        this.operationType = operationType;
        this.comments = comments;
        this.ctime = new Date();
    }

    // Getter和Setter方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorGroupCode() {
        return operatorGroupCode;
    }

    public void setOperatorGroupCode(String operatorGroupCode) {
        this.operatorGroupCode = operatorGroupCode;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    @Override
    public String toString() {
        return "ApprovalHistory{" +
                "id=" + id +
                ", applicationId=" + applicationId +
                ", taskId=" + taskId +
                ", operatorId=" + operatorId +
                ", operatorName='" + operatorName + '\'' +
                ", operatorGroupCode='" + operatorGroupCode + '\'' +
                ", operationType='" + operationType + '\'' +
                ", comments='" + comments + '\'' +
                ", ctime=" + ctime +
                '}';
    }
} 