package com.example.cffexmember.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import java.util.List;

/**
 * 申请详情响应DTO
 */
@Data
public class ApplicationDetailResponse {
    private String applicationId;
    private String memberName;
    private String status;
    private String submittedAt;
    private JsonNode formData;
    private List<Attachment> attachments;

//    @Data
//    public static class FormData {
//        private String memberName;
//        private String memberType;
//    }

    @Data
    public static class Attachment {
        private String attachmentId;
        private String fileName;
        private String downloadUrl;
    }
} 