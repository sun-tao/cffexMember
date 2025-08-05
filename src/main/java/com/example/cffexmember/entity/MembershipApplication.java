package com.example.cffexmember.entity;

import java.util.Date;

/**
 * 会员入会申请实体类
 */
public class MembershipApplication {
    private Integer id;
    private Integer applicantUserId;
    private String applicantUserName;
    private String formData;
    private Integer attachmentId;
    private String status; // PROCESSING, RETURNED, APPROVED, REJECTED
    private Integer currentNodeId;
    private Integer returnSourceNodeId;
    private Date ctime;
    private Date mtime;

    // 构造函数
    public MembershipApplication() {}

    public MembershipApplication(Integer applicantUserId, String applicantUserName, String formData) {
        this.applicantUserId = applicantUserId;
        this.applicantUserName = applicantUserName;
        this.formData = formData;
        this.status = "PROCESSING";
        this.ctime = new Date();
        this.mtime = new Date();
    }

    // Getter和Setter方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCurrentNodeId() {
        return currentNodeId;
    }

    public void setCurrentNodeId(Integer currentNodeId) {
        this.currentNodeId = currentNodeId;
    }

    public Integer getReturnSourceNodeId() {
        return returnSourceNodeId;
    }

    public void setReturnSourceNodeId(Integer returnSourceNodeId) {
        this.returnSourceNodeId = returnSourceNodeId;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    @Override
    public String toString() {
        return "MembershipApplication{" +
                "id=" + id +
                ", applicantUserId=" + applicantUserId +
                ", applicantUserName='" + applicantUserName + '\'' +
                ", formData='" + formData + '\'' +
                ", attachmentId=" + attachmentId +
                ", status='" + status + '\'' +
                ", currentNodeId=" + currentNodeId +
                ", returnSourceNodeId=" + returnSourceNodeId +
                ", ctime=" + ctime +
                ", mtime=" + mtime +
                '}';
    }
} 