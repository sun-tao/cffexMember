package com.example.cffexmember.mapper;

import com.example.cffexmember.entity.ApprovalHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 审批历史Mapper接口
 */
@Mapper
public interface ApprovalHistoryMapper {
    
    /**
     * 插入审批历史
     */
    int insert(ApprovalHistory history);
    
    /**
     * 根据ID查询历史记录
     */
    ApprovalHistory selectById(@Param("id") Integer id);
    
    /**
     * 根据申请ID查询历史记录
     */
    List<ApprovalHistory> selectByApplicationId(@Param("applicationId") Integer applicationId);
    
    /**
     * 根据任务ID查询历史记录
     */
    List<ApprovalHistory> selectByTaskId(@Param("taskId") Integer taskId);
    
    /**
     * 根据操作人ID查询历史记录
     */
    List<ApprovalHistory> selectByOperatorId(@Param("operatorId") Integer operatorId);
    
    /**
     * 查询所有历史记录
     */
    List<ApprovalHistory> selectAll();
} 