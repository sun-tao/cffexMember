package com.example.cffexmember.controller;

import com.example.cffexmember.dto.ApiResponse;
import com.example.cffexmember.dto.ApprovalRequest;
import com.example.cffexmember.dto.PageResponse;
import com.example.cffexmember.dto.TaskListItem;
import com.example.cffexmember.dto.ApprovalHistoryItem;
import com.example.cffexmember.entity.ApprovalHistory;
import com.example.cffexmember.entity.ApprovalTask;
import com.example.cffexmember.entity.User;
import com.example.cffexmember.entity.MembershipApplication;
import com.example.cffexmember.service.ApprovalService;
import com.example.cffexmember.service.UserService;
import com.example.cffexmember.service.MembershipApplicationService;
import com.example.cffexmember.service.ProcessNodeService;
//import com.example.cffexmember.util.SecurityUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

/**
 * 审批控制器
 */
@RestController
@RequestMapping("/")
@Validated
@Slf4j
public class ApprovalController {
    
    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private MembershipApplicationService applicationService;
    
    @Autowired
    private ProcessNodeService processNodeService;
    
//    @Autowired
//    private SecurityUtil securityUtil;
    
    /**
     * 审批操作
     */
    @PostMapping("/approve")
    public ApiResponse<Boolean> approve(@Valid @RequestBody ApprovalRequest request) {
        try {
            // TODO: 从登陆信息中获取用户信息
            Integer currentUserId = 6;
            String currentUsername = "manager001";
            String currentUserGroupCode = "management";
            
            if (currentUserId == null || currentUsername == null || currentUserGroupCode == null) {
                return ApiResponse.error(401, "用户信息不完整");
            }
            
            // 检查权限：只有审批人员可以操作
            if ("member".equals(currentUserGroupCode)) {
                return ApiResponse.error(403, "会员不能进行审批操作");
            }

            // 1. 根据当前用户组查找对应的节点名称
            String nodeName = processNodeService.getNodeNameByHandlerGroupCode(currentUserGroupCode);
            if (nodeName == null) {
                return ApiResponse.error(400, "未找到对应的审批节点");
            }
            
            // 2. 查找当前用户要处理的任务
            ApprovalTask currentTask = approvalService.getPendingTaskByApplicationAndNode(
                request.getApplicationId(), nodeName);
            if (currentTask == null) {
                return ApiResponse.error(404, "未找到待处理的任务");
            }
            
            // 3. 创建审批历史记录
            ApprovalHistory history = new ApprovalHistory(
                request.getApplicationId(),
                currentTask.getId(), // 使用找到的任务ID
                currentUserId,
                currentUsername,
                currentUserGroupCode,
                request.getOperationType(),
                request.getComments()
            );
            
            boolean result = approvalService.approve(history);
            return ApiResponse.success(result);
        } catch (Exception e) {
            log.error("审批操作失败", e);
            return ApiResponse.error("审批操作失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据当前用户所处的处理人组，查询待处理的任务
     */
    @GetMapping("/tasks/queryAll")
    public ApiResponse<PageResponse<TaskListItem>> getPendingTasksByHandlerGroup(@RequestParam(required = false) String username) {
        try {
            // TODO:根据当前的登陆用户查询所属的用户组
//            int currentUserId = 2;
            int pageNo = 1;
            int pageSize = 50;
            User u = userService.findByUsername(username);
            String handlerGroupCode = u.getUsergroupCode();
            
            // 获取任务列表
            List<ApprovalTask> tasks = approvalService.getPendingTasksByHandlerGroup(handlerGroupCode, pageNo, pageSize);
            long total = approvalService.getTotalPendingTasks(handlerGroupCode);
            
            // 转换为TaskListItem列表
            List<TaskListItem> taskListItems = new ArrayList<>();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (ApprovalTask task : tasks) {
                // 根据申请ID获取申请信息
                MembershipApplication application = applicationService.getApplicationById(task.getApplicationId());
                if (application != null) {
                    TaskListItem item = new TaskListItem(
                        String.valueOf(application.getId()),
                        getMemberNameByApplication(application), // 这里可以根据需要调整字段
                        application.getApplicantUserName(),
                        formatter.format(application.getCtime())
                    );
                    taskListItems.add(item);
                }
            }
            
            // 创建分页响应
            PageResponse<TaskListItem> pageResponse = new PageResponse<>(taskListItems, pageNo, pageSize, total);
            
            return ApiResponse.success(pageResponse);
        } catch (Exception e) {
            return ApiResponse.error("查询待处理任务失败: " + e.getMessage());
        }
    }

    
    /**
     * 根据申请ID查询审批历史
     */
    @GetMapping("/application/queryDetail")
    public ApiResponse<List<ApprovalHistory>> getHistoryByApplicationId(@RequestParam Integer applicationId) {
        try {
            // TODO:需要做水平鉴权，申请人必须是自己才能查
            List<ApprovalHistory> history = approvalService.getHistoryByApplicationId(applicationId);
            return ApiResponse.success(history);
        } catch (Exception e) {
            return ApiResponse.error("查询审批历史失败: " + e.getMessage());
        }
    }
    
    /**
     * 查询当前用户所在部门的审批历史记录
     */
    @GetMapping("/task/history")
    public ApiResponse<PageResponse<ApprovalHistoryItem>> getHistoryByCurrentUserGroup(@RequestParam int pageNo, @RequestParam int pageSize) {
        try {
            // TODO: 从登录信息中获取当前用户组
            String currentUserGroupCode = "trade_dept_junior"; // 这里应该从session中获取
            
            // 获取审批历史列表
            List<ApprovalHistory> historyList = approvalService.getHistoryByOperatorGroupCode(currentUserGroupCode, pageNo, pageSize);
            long total = approvalService.getHistoryCountByOperatorGroupCode(currentUserGroupCode);
            
            // 转换为ApprovalHistoryItem列表
            List<ApprovalHistoryItem> historyItems = new ArrayList<>();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            for (ApprovalHistory history : historyList) {
                // 获取申请信息
                MembershipApplication application = applicationService.getApplicationById(history.getApplicationId());
                String memberName = "";
                if (application != null) {
                    memberName = getMemberNameByApplication(application);
                }
                
                // 获取节点名称
                String nodeName = processNodeService.getNodeNameByHandlerGroupCode(history.getOperatorGroupCode());
                
                ApprovalHistoryItem item = new ApprovalHistoryItem(
                    history.getId(),
                    history.getApplicationId(),
                    memberName,
                    nodeName,
                    history.getOperationType(),
                    history.getComments(),
                    formatter.format(history.getCtime())
                );
                historyItems.add(item);
            }
            
            // 创建分页响应
            PageResponse<ApprovalHistoryItem> pageResponse = new PageResponse<>(historyItems, pageNo, pageSize, total);
            
            return ApiResponse.success(pageResponse);
        } catch (Exception e) {
            log.error("查询审批历史失败", e);
            return ApiResponse.error("查询审批历史失败: " + e.getMessage());
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