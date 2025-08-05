package com.example.cffexmember.service;

import com.example.cffexmember.entity.MembershipApplication;
import com.example.cffexmember.entity.ProcessNode;
import com.example.cffexmember.entity.ApprovalTask;
import com.example.cffexmember.mapper.MembershipApplicationMapper;
import com.example.cffexmember.mapper.ProcessNodeMapper;
import com.example.cffexmember.mapper.ApprovalTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 会员申请服务类
 */
@Service
public class MembershipApplicationService {
    
    @Autowired
    private MembershipApplicationMapper applicationMapper;
    
    @Autowired
    private ProcessNodeMapper processNodeMapper;
    
    @Autowired
    private ApprovalTaskMapper approvalTaskMapper;
    
    /**
     * 提交申请
     */
    @Transactional
    public MembershipApplication submitApplication(MembershipApplication application) {
        // 1. 插入申请记录
        applicationMapper.insert(application);
        
        // 2. 获取第一个流程节点（START类型）
        ProcessNode startNode = processNodeMapper.selectByNodeType("START").get(0);
        
        // 3. 设置当前节点
        application.setCurrentNodeId(startNode.getOnApproveNodeId());
        applicationMapper.update(application);
        
        // 4. 创建第一个审批任务
        ProcessNode firstTaskNode = processNodeMapper.selectById(startNode.getOnApproveNodeId());
        ApprovalTask task = new ApprovalTask(application.getId(), firstTaskNode.getId(), firstTaskNode.getNodeName());
        approvalTaskMapper.insert(task);
        
        return application;
    }
    
    /**
     * 根据ID查询申请
     */
    public MembershipApplication getApplicationById(Integer id) {
        return applicationMapper.selectById(id);
    }
    
    /**
     * 根据申请人ID查询申请列表
     */
    public List<MembershipApplication> getApplicationsByApplicantUserId(Integer applicantUserId) {
        return applicationMapper.selectByApplicantUserId(applicantUserId);
    }
    
    /**
     * 根据状态查询申请列表
     */
    public List<MembershipApplication> getApplicationsByStatus(String status) {
        return applicationMapper.selectByStatus(status);
    }
    
    /**
     * 查询所有申请
     */
    public List<MembershipApplication> getAllApplications() {
        return applicationMapper.selectAll();
    }
    
    /**
     * 更新申请状态
     */
    public int updateApplicationStatus(Integer id, String status, Integer currentNodeId) {
        return applicationMapper.updateStatus(id, status, currentNodeId);
    }
} 