package com.example.cffexmember.controller;

import com.example.cffexmember.dto.ApiResponse;
import com.example.cffexmember.dto.ApplicationRequest;
import com.example.cffexmember.entity.MembershipApplication;
import com.example.cffexmember.service.MembershipApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 申请控制器
 */
@RestController
@RequestMapping("/api/applications")
@Validated
public class ApplicationController {
    
    @Autowired
    private MembershipApplicationService applicationService;
    
    /**
     * 提交申请
     */
    @PostMapping("/submit")
    public ApiResponse<MembershipApplication> submitApplication(@Valid @RequestBody ApplicationRequest request) {
        try {
            MembershipApplication application = new MembershipApplication(
                request.getApplicantUserId(),
                request.getApplicantUserName(),
                request.getFormData()
            );
            application.setAttachmentId(request.getAttachmentId());
            
            MembershipApplication result = applicationService.submitApplication(application);
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("提交申请失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据ID查询申请
     */
    @GetMapping("/{id}")
    public ApiResponse<MembershipApplication> getApplicationById(@PathVariable Integer id) {
        try {
            MembershipApplication application = applicationService.getApplicationById(id);
            if (application == null) {
                return ApiResponse.error(404, "申请不存在");
            }
            return ApiResponse.success(application);
        } catch (Exception e) {
            return ApiResponse.error("查询申请失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据申请人ID查询申请列表
     */
    @GetMapping("/applicant/{applicantUserId}")
    public ApiResponse<List<MembershipApplication>> getApplicationsByApplicantUserId(@PathVariable Integer applicantUserId) {
        try {
            List<MembershipApplication> applications = applicationService.getApplicationsByApplicantUserId(applicantUserId);
            return ApiResponse.success(applications);
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