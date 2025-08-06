package com.example.cffexmember.service;

import com.example.cffexmember.entity.ApprovalTask;
import com.example.cffexmember.entity.ApprovalHistory;
import com.example.cffexmember.entity.MembershipApplication;
import com.example.cffexmember.entity.ProcessNode;
import com.example.cffexmember.mapper.ApprovalTaskMapper;
import com.example.cffexmember.mapper.ApprovalHistoryMapper;
import com.example.cffexmember.mapper.MembershipApplicationMapper;
import com.example.cffexmember.mapper.ProcessNodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 审批服务类
 */
@Service
public class ApprovalService {
    
    @Autowired
    private ApprovalTaskMapper approvalTaskMapper;
    
    @Autowired
    private ApprovalHistoryMapper approvalHistoryMapper;
    
    @Autowired
    private MembershipApplicationMapper applicationMapper;
    
    @Autowired
    private ProcessNodeMapper processNodeMapper;
    
    /**
     * 审批操作
     */
    @Transactional
    public boolean approve(ApprovalHistory history) {
        // 1. 记录审批历史
        approvalHistoryMapper.insert(history);
        
        // 2. 完成当前任务
        ApprovalTask currentTask = approvalTaskMapper.selectById(history.getTaskId());
        currentTask.setTaskStatus("COMPLETED");
        approvalTaskMapper.update(currentTask);
        
        // 3. 获取申请信息
        MembershipApplication application = applicationMapper.selectById(history.getApplicationId());
        
        // 4. 获取当前节点信息
        ProcessNode currentNode = processNodeMapper.selectById(currentTask.getNodeId());
        
        // 5. 根据操作类型处理
        switch (history.getOperationType()) {
            case "APPROVE":
                return handleApprove(application, currentNode);
            case "REJECT":
                return handleReject(application, currentNode);
//            case "RETURN":
//                return handleReturn(application, currentNode);
            default:
                throw new IllegalArgumentException("不支持的操作类型: " + history.getOperationType());
        }
    }
    
    /**
     * 根据申请ID和节点名称查找待处理任务
     */
    public ApprovalTask getPendingTaskByApplicationAndNode(Integer applicationId, String nodeName) {
        return approvalTaskMapper.selectPendingByApplicationAndNode(applicationId, nodeName);
    }
    
    /**
     * 处理通过操作
     */
    private boolean handleApprove(MembershipApplication application, ProcessNode currentNode) {
        if ("END".equals(currentNode.getNodeType())) {
            // 流程结束，申请通过
            application.setStatus("APPROVED");
            applicationMapper.update(application);
            return true;
        } else {
            // 创建下一个任务
            ProcessNode nextNode = processNodeMapper.selectById(currentNode.getOnApproveNodeId());
            ApprovalTask nextTask = new ApprovalTask(application.getId(), nextNode.getId(), nextNode.getNodeName());
            approvalTaskMapper.insert(nextTask);
            
            // 更新申请当前节点
            application.setCurrentNodeId(nextNode.getId());
            applicationMapper.update(application);
            return true;
        }
    }
    
    /**
     * 处理拒绝操作
     */
    private boolean handleReject(MembershipApplication application, ProcessNode currentNode) {
        // 申请被拒绝，流程结束
        ProcessNode rejectNode = processNodeMapper.selectById(currentNode.getOnRejectNodeId());
        application.setStatus("REJECTED");
        application.setCurrentNodeId(rejectNode.getId());
        applicationMapper.update(application);
        return true;
    }
    
    /**
     * 处理退回操作
     */
    private boolean handleReturn(MembershipApplication application, ProcessNode currentNode) {
        // 设置退回状态
        application.setStatus("RETURNED");
        application.setReturnSourceNodeId(currentNode.getId());
        applicationMapper.update(application);
        return true;
    }
    
    /**
     * 根据处理人组查询待处理任务
     */
    public List<ApprovalTask> getPendingTasksByHandlerGroup(String handlerGroupCode,int pageNo,int pageSize) {
        int offset = (pageNo - 1) * pageSize;
        int limit = pageSize;
        return approvalTaskMapper.selectPendingByHandlerGroup(handlerGroupCode,offset,limit);
    }
    
    /**
     * 根据处理人组查询待处理任务总数
     */
    public long getTotalPendingTasks(String handlerGroupCode) {
        return approvalTaskMapper.countPendingByHandlerGroup(handlerGroupCode);
    }
    
    /**
     * 根据申请ID查询任务列表
     */
    public List<ApprovalTask> getTasksByApplicationId(Integer applicationId) {
        return approvalTaskMapper.selectByApplicationId(applicationId);
    }
    
    /**
     * 根据申请ID查询审批历史
     */
    public List<ApprovalHistory> getHistoryByApplicationId(Integer applicationId) {
        return approvalHistoryMapper.selectByApplicationId(applicationId);
    }
    
    /**
     * 根据任务ID查询审批历史
     */
    public List<ApprovalHistory> getHistoryByTaskId(Integer taskId) {
        return approvalHistoryMapper.selectByTaskId(taskId);
    }
} 