package com.example.cffexmember.mapper;

import com.example.cffexmember.entity.ApplicationAttachment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 申请附件Mapper接口
 */
@Mapper
public interface ApplicationAttachmentMapper {
    
    /**
     * 根据ID查询附件
     */
    ApplicationAttachment selectById(@Param("id") Integer id);

    
    /**
     * 根据申请ID查询附件列表
     */
    List<ApplicationAttachment> selectByApplicationId(@Param("applicationId") Integer applicationId);
    
    /**
     * 插入附件
     */
    int insert(ApplicationAttachment attachment);
    
    /**
     * 更新附件
     */
    int update(ApplicationAttachment attachment);
    
    /**
     * 删除附件
     */
    int deleteById(@Param("id") Integer id);
    
    /**
     * 查询所有附件
     */
    List<ApplicationAttachment> selectAll();
} 