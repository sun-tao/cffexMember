package com.example.cffexmember.controller;

import com.example.cffexmember.dto.ApiResponse;
import com.example.cffexmember.dto.ApprovalRequest;
import com.example.cffexmember.entity.ApprovalHistory;
import com.example.cffexmember.entity.ApprovalTask;
import com.example.cffexmember.service.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 审批控制器
 */
@RestController
@RequestMapping("/api/approvals")
@Validated
public class ApprovalController {
    
    @Autowired
    private ApprovalService approvalService;
    
    /**
     * 审批操作
     */
    @PostMapping("/approve")
    public ApiResponse<Boolean> approve(@Valid @RequestBody ApprovalRequest request) {
        try {
            ApprovalHistory history = new ApprovalHistory(
                request.getApplicationId(),
                request.getTaskId(),
                request.getOperatorId(),
                request.getOperatorName(),
                request.getOperatorGroupCode(),
                request.getOperationType(),
                request.getComments()
            );
            
            boolean result = approvalService.approve(history);
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("审批操作失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据处理人组查询待处理任务
     */
    @GetMapping("/tasks/pending/{handlerGroupCode}")
    public ApiResponse<List<ApprovalTask>> getPendingTasksByHandlerGroup(@PathVariable String handlerGroupCode) {
        try {
            List<ApprovalTask> tasks = approvalService.getPendingTasksByHandlerGroup(handlerGroupCode);
            return ApiResponse.success(tasks);
        } catch (Exception e) {
            return ApiResponse.error("查询待处理任务失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据申请ID查询任务列表
     */
    @GetMapping("/tasks/application/{applicationId}")
    public ApiResponse<List<ApprovalTask>> getTasksByApplicationId(@PathVariable Integer applicationId) {
        try {
            List<ApprovalTask> tasks = approvalService.getTasksByApplicationId(applicationId);
            return ApiResponse.success(tasks);
        } catch (Exception e) {
            return ApiResponse.error("查询任务列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据申请ID查询审批历史
     */
    @GetMapping("/history/application/{applicationId}")
    public ApiResponse<List<ApprovalHistory>> getHistoryByApplicationId(@PathVariable Integer applicationId) {
        try {
            List<ApprovalHistory> history = approvalService.getHistoryByApplicationId(applicationId);
            return ApiResponse.success(history);
        } catch (Exception e) {
            return ApiResponse.error("查询审批历史失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据任务ID查询审批历史
     */
    @GetMapping("/history/task/{taskId}")
    public ApiResponse<List<ApprovalHistory>> getHistoryByTaskId(@PathVariable Integer taskId) {
        try {
            List<ApprovalHistory> history = approvalService.getHistoryByTaskId(taskId);
            return ApiResponse.success(history);
        } catch (Exception e) {
            return ApiResponse.error("查询审批历史失败: " + e.getMessage());
        }
    }
} 