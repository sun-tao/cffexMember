package com.example.cffexmember.dto;

import javax.validation.constraints.NotNull;

/**
 * 审批请求DTO
 */
public class ApprovalRequest {
    @NotNull(message = "申请ID不能为空")
    private Integer applicationId;
    
    @NotNull(message = "操作类型不能为空")
    private String operationType; // APPROVE, REJECT, RETURN
    
    private String comments;

    // 构造函数
    public ApprovalRequest() {}

    public ApprovalRequest(Integer applicationId, String operationType, String comments) {
        this.applicationId = applicationId;
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
                ", operationType='" + operationType + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
} 