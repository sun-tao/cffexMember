package com.example.cffexmember.dto;

import javax.validation.constraints.NotNull;

/**
 * 申请请求DTO
 */
public class ApplicationRequest {
    @NotNull(message = "申请人ID不能为空")
    private Integer applicantUserId;
    
    @NotNull(message = "申请人姓名不能为空")
    private String applicantUserName;
    
    @NotNull(message = "申请表单数据不能为空")
    private String formData;
    
    private Integer attachmentId;

    // 构造函数
    public ApplicationRequest() {}

    public ApplicationRequest(Integer applicantUserId, String applicantUserName, String formData) {
        this.applicantUserId = applicantUserId;
        this.applicantUserName = applicantUserName;
        this.formData = formData;
    }

    // Getter和Setter方法
    public Integer getApplicantUserId() {
        return applicantUserId;
    }

    public void setApplicantUserId(Integer applicantUserId) {
        this.applicantUserId = applicantUserId;
    }

    public String getApplicantUserName() {
        return applicantUserName;
    }

    public void setApplicantUserName(String applicantUserName) {
        this.applicantUserName = applicantUserName;
    }

    public String getFormData() {
        return formData;
    }

    public void setFormData(String formData) {
        this.formData = formData;
    }

    public Integer getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Integer attachmentId) {
        this.attachmentId = attachmentId;
    }

    @Override
    public String toString() {
        return "ApplicationRequest{" +
                "applicantUserId=" + applicantUserId +
                ", applicantUserName='" + applicantUserName + '\'' +
                ", formData='" + formData + '\'' +
                ", attachmentId=" + attachmentId +
                '}';
    }
} 