package com.example.cffexmember.dto;

import javax.validation.constraints.NotNull;

/**
 * 审批请求DTO
 */
public class ApprovalRequest {
    @NotNull(message = "申请ID不能为空")
    private Integer applicationId;
    
    @NotNull(message = "任务ID不能为空")
    private Integer taskId;
    
    @NotNull(message = "操作人ID不能为空")
    private Integer operatorId;
    
    @NotNull(message = "操作人姓名不能为空")
    private String operatorName;
    
    @NotNull(message = "操作人用户组不能为空")
    private String operatorGroupCode;
    
    @NotNull(message = "操作类型不能为空")
    private String operationType; // APPROVE, REJECT, RETURN
    
    private String comments;

    // 构造函数
    public ApprovalRequest() {}

    public ApprovalRequest(Integer applicationId, Integer taskId, Integer operatorId, 
                         String operatorName, String operatorGroupCode, 
                         String operationType, String comments) {
        this.applicationId = applicationId;
        this.taskId = taskId;
        this.operatorId = operatorId;
        this.operatorName = operatorName;
        this.operatorGroupCode = operatorGroupCode;
        this.operationType = operationType;
        this.comments = comments;
    }

    // Getter和Setter方法
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

    @Override
    public String toString() {
        return "ApprovalRequest{" +
                "applicationId=" + applicationId +
                ", taskId=" + taskId +
                ", operatorId=" + operatorId +
                ", operatorName='" + operatorName + '\'' +
                ", operatorGroupCode='" + operatorGroupCode + '\'' +
                ", operationType='" + operationType + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
} 