package com.example.cffexmember.mapper;

import com.example.cffexmember.entity.MembershipApplication;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 会员申请Mapper接口
 */
@Mapper
public interface MembershipApplicationMapper {
    
    /**
     * 插入申请记录
     */
    int insert(MembershipApplication application);
    
    /**
     * 根据ID查询申请
     */
    MembershipApplication selectById(@Param("id") Integer id);
    
    /**
     * 根据申请人ID查询申请列表
     */
    List<MembershipApplication> selectByApplicantUserId(@Param("applicantUserId") Integer applicantUserId);
    
    /**
     * 根据状态查询申请列表
     */
    List<MembershipApplication> selectByStatus(@Param("status") String status);
    
    /**
     * 更新申请状态
     */
    int updateStatus(@Param("id") Integer id, @Param("status") String status, @Param("currentNodeId") Integer currentNodeId);
    
    /**
     * 更新申请信息
     */
    int update(MembershipApplication application);
    
    /**
     * 查询所有申请
     */
    List<MembershipApplication> selectAll();
} 