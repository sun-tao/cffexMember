package com.example.cffexmember.controller;

import com.example.cffexmember.dto.*;
import com.example.cffexmember.entity.ApplicationRecord;
import com.example.cffexmember.entity.MembershipApplication;
import com.example.cffexmember.entity.ApplicationAttachment;
import com.example.cffexmember.entity.User;
import com.example.cffexmember.service.MembershipApplicationService;
import com.example.cffexmember.service.ApplicationAttachmentService;
//import com.example.cffexmember.util.SecurityUtil;
import com.example.cffexmember.service.UserService;
import com.example.cffexmember.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private UserService userService;

    /**
     * 提交申请
     */
    @PostMapping("/application/submit")
    public ApiResponse<MembershipApplication> submitApplication(@Valid @RequestBody ApplicationRequest request,HttpServletRequest httpServletRequest) {
        try {
            // TODO: 从session中拿
            CookieUtil cookieUtil = new CookieUtil();
            String username = cookieUtil.readCookieValue(httpServletRequest, "cookie");
            User currUser = userService.findByUsername(username);

            log.debug("会员创建申请 {}",request);
            int currentUserId = currUser.getId();
            String currentUsername = currUser.getUsername();
            
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
    public ApiResponse<PageResponse<ApplicationRecord>> getApplicationsByApplicantUserId(@RequestParam(required = false) String status, @RequestParam int pageNo, @RequestParam int pageSize, HttpServletRequest request) {
        try {
            // TODO:从session中拿,先mock
            CookieUtil cookieUtil = new CookieUtil();
            String username = cookieUtil.readCookieValue(request, "cookie");
            User currUser = userService.findByUsername(username);
            int applicantUserId = currUser.getId(); // 会员
            List<MembershipApplication> applications = applicationService.getApplicationsByApplicantUserId(applicantUserId,status,pageNo,pageSize);
            int total = applicationService.getTotalApplication(applicantUserId,status);
            List<ApplicationRecord> applicationRecords = new ArrayList<>();
            for (MembershipApplication application : applications){
                ApplicationRecord applicationRecord = new ApplicationRecord();
                applicationRecord.setApplicationId(application.getId());
                applicationRecord.setStatus(application.getStatus());
                applicationRecord.setMemberName(getMemberNameByApplication(application));
                // 定义格式
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                // 转换为字符串
                String dateString = formatter.format(application.getCtime());
                applicationRecord.setSubmitAt(dateString);
                applicationRecords.add(applicationRecord);
            }
            // 创建分页响应
            PageResponse<ApplicationRecord> pageResponse = new PageResponse<>(applicationRecords, pageNo, pageSize, total);
            return ApiResponse.success(pageResponse);
        } catch (Exception e) {
            return ApiResponse.error("查询申请列表失败: " + e.getMessage());
        }
    }


    private String getMemberNameByApplication(MembershipApplication application){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode formDataNode = objectMapper.readTree(application.getFormData());
            return formDataNode.has("memberName") ? formDataNode.get("memberName").asText() : "";
        } catch (Exception e) {
            // 如果JSON解析失败，创建空的formData
            log.error("ApprovalController中getMemberNameByApplication的formData的json无法解析");
        }
        return "";
    }
} 