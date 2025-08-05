package com.example.cffexmember.mapper;

import com.example.cffexmember.entity.ApprovalTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 审批任务Mapper接口
 */
@Mapper
public interface ApprovalTaskMapper {
    
    /**
     * 插入审批任务
     */
    int insert(ApprovalTask task);
    
    /**
     * 根据ID查询任务
     */
    ApprovalTask selectById(@Param("id") Integer id);
    
    /**
     * 根据申请ID查询任务列表
     */
    List<ApprovalTask> selectByApplicationId(@Param("applicationId") Integer applicationId);
    
    /**
     * 根据申请ID和状态查询任务
     */
    ApprovalTask selectByApplicationIdAndStatus(@Param("applicationId") Integer applicationId, @Param("taskStatus") String taskStatus);
    
    /**
     * 更新任务状态
     */
    int updateStatus(@Param("id") Integer id, @Param("taskStatus") String taskStatus);
    
    /**
     * 更新任务信息
     */
    int update(ApprovalTask task);
    
    /**
     * 根据处理人组代码查询待处理任务
     */
    List<ApprovalTask> selectPendingByHandlerGroup(@Param("handlerGroupCode") String handlerGroupCode);
    
    /**
     * 查询所有任务
     */
    List<ApprovalTask> selectAll();
} 