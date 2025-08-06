package com.example.cffexmember.controller;

import com.example.cffexmember.dto.ApiResponse;
import com.example.cffexmember.dto.ApplicationOverView;
import com.example.cffexmember.dto.ApplicationRequest;
import com.example.cffexmember.dto.ApplicationDetailResponse;
import com.example.cffexmember.entity.ApplicationRecord;
import com.example.cffexmember.entity.MembershipApplication;
import com.example.cffexmember.entity.ApplicationAttachment;
import com.example.cffexmember.service.MembershipApplicationService;
import com.example.cffexmember.service.ApplicationAttachmentService;
//import com.example.cffexmember.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * 申请控制器
 */
@RestController
@RequestMapping("/")
@Validated
@Slf4j
public class ApplicationController {

    @Autowired
    private MembershipApplicationService applicationService;
    
    @Autowired
    private ApplicationAttachmentService attachmentService;

    /**
     * 提交申请
     */
    @PostMapping("/application/submit")
    public ApiResponse<MembershipApplication> submitApplication(@Valid @RequestBody ApplicationRequest request) {
        try {
            // TODO: 从session中拿
            log.debug("会员创建申请 {}",request);
            int currentUserId = 1;
            String currentUsername = "test";
            
            MembershipApplication application = new MembershipApplication(
                currentUserId, // 使用当前用户ID
                currentUsername, // 使用当前用户名
                request.getFormData()
            );
            application.setAttachmentId(request.getAttachments().getAttachmentId());
            
            applicationService.submitApplication(application);
            return ApiResponse.success();
        } catch (Exception e) {
            return ApiResponse.error("提交申请失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据ID查询申请详情
     */
    @GetMapping("/application/queryone")
    public ApiResponse<ApplicationDetailResponse> getApplicationById(@RequestParam Integer id) {
        try {
            MembershipApplication application = applicationService.getApplicationById(id);
            if (application == null) {
                return ApiResponse.error(404, "申请不存在");
            }
            
            // 转换为期望的响应格式
            ApplicationDetailResponse response = new ApplicationDetailResponse();
            response.setApplicationId(String.valueOf(application.getId()));
            response.setMemberName(application.getApplicantUserName());
            response.setStatus(application.getStatus());
            
            // 格式化提交时间
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            response.setSubmittedAt(formatter.format(application.getCtime()));
            response.setFormData(application.getFormData());
            // 解析formData JSON
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode formDataNode = objectMapper.readTree(application.getFormData());
                response.setMemberName(formDataNode.has("memberName") ? formDataNode.get("memberName").asText() : "");
            } catch (Exception e) {
                // 如果JSON解析失败，创建空的formData
                log.error("formData的json无法解析");
            }
            
            // 处理附件信息（如果有的话）
            List<ApplicationDetailResponse.Attachment> attachments = new ArrayList<>();
            if (application.getAttachmentId() != null) {
                // 根据附件ID查询真实的附件信息
                ApplicationAttachment attachmentInfo = attachmentService.getById(application.getAttachmentId());
                if (attachmentInfo != null) {
                    ApplicationDetailResponse.Attachment attachment = new ApplicationDetailResponse.Attachment();
                    attachment.setAttachmentId(String.valueOf(attachmentInfo.getId()));
                    attachment.setFileName(attachmentInfo.getFileName());
                    attachment.setDownloadUrl(attachmentInfo.getDownloadUrl());
                    attachments.add(attachment);
                }
            }
            response.setAttachments(attachments);
            
            return ApiResponse.success(response);
        } catch (Exception e) {
            return ApiResponse.error("查询申请失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据申请人ID查询申请列表
     */
    @GetMapping("/application/queryAll")
    public ApiResponse<ApplicationOverView> getApplicationsByApplicantUserId(@RequestParam(required = false) String status,@RequestParam int pageNo,@RequestParam int pageSize) {
        try {
            // TODO:从session中拿,先mock
            int applicantUserId = 1; // 会员
            List<MembershipApplication> applications = applicationService.getApplicationsByApplicantUserId(applicantUserId,status,pageNo,pageSize);
            int total = applicationService.getTotalApplication(applicantUserId,status);
            ApplicationOverView applicationOverView = new ApplicationOverView();
            applicationOverView.setPageNo(pageNo);
            applicationOverView.setPageSize(pageSize);
            applicationOverView.setTotal(total);
            List<ApplicationRecord> applicationRecords = new ArrayList<>();
            for (MembershipApplication application : applications){
                ApplicationRecord applicationRecord = new ApplicationRecord();
                applicationRecord.setApplicationId(application.getId());
                applicationRecord.setStatus(application.getStatus());
                // 定义格式
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                // 转换为字符串
                String dateString = formatter.format(application.getCtime());
                applicationRecord.setSubmitAt(dateString);
                applicationRecords.add(applicationRecord);
            }
            applicationOverView.setApplicationRecord(applicationRecords);
            return ApiResponse.success(applicationOverView);
        } catch (Exception e) {
            return ApiResponse.error("查询申请列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据状态查询申请列表
     */
    @GetMapping("/status/{status}")
    public ApiResponse<List<MembershipApplication>> getApplicationsByStatus(@PathVariable String status) {
        try {
            List<MembershipApplication> applications = applicationService.getApplicationsByStatus(status);
            return ApiResponse.success(applications);
        } catch (Exception e) {
            return ApiResponse.error("查询申请列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 查询所有申请
     */
    @GetMapping("/all")
    public ApiResponse<List<MembershipApplication>> getAllApplications() {
        try {
            List<MembershipApplication> applications = applicationService.getAllApplications();
            return ApiResponse.success(applications);
        } catch (Exception e) {
            return ApiResponse.error("查询申请列表失败: " + e.getMessage());
        }
    }
} 