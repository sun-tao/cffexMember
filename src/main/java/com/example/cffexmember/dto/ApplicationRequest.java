package com.example.cffexmember.dto;

import com.fasterxml.jackson.databind.JsonNode;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * 申请请求DTO
 */
public class ApplicationRequest {
//    @NotNull(message = "申请表单数据不能为空")
//    private String formData;

    private JsonNode formData;

    // 定义 attachments 字段
    private Attachments attachments;

    // getter and setter for formData

    public static class Attachments {
        private Integer attachmentId;  // 假设 attachmentId 是整数类型

        public Integer getAttachmentId() {
            return attachmentId;
        }

        public void setAttachmentId(Integer attachmentId) {
            this.attachmentId = attachmentId;
        }
    }

    // getter and setter for attachments
    public Attachments getAttachments() {
        return attachments;
    }

    public void setAttachments(Attachments attachments) {
        this.attachments = attachments;
    }

    // 构造函数
    public ApplicationRequest() {}

    public ApplicationRequest(JsonNode formData) {
        this.formData = formData;
    }

    // Getter和Setter方法
    public JsonNode getFormData() {
        return formData;
    }

    public void setFormData(JsonNode formData) {
        this.formData = formData;
    }


    public void setAttachmentId(Attachments attachments) {
        this.attachments = attachments;
    }


    @Override
    public String toString() {
        return "ApplicationRequest{" +
                "formData='" + formData + '\'' +
                ", attachments=" + attachments +
                '}';
    }
} 