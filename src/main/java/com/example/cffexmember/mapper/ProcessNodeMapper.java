package com.example.cffexmember.mapper;

import com.example.cffexmember.entity.ProcessNode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 流程节点Mapper接口
 */
@Mapper
public interface ProcessNodeMapper {
    
    /**
     * 插入节点配置
     */
    int insert(ProcessNode node);
    
    /**
     * 根据ID查询节点
     */
    ProcessNode selectById(@Param("id") Integer id);
    
    /**
     * 根据节点类型查询节点列表
     */
    List<ProcessNode> selectByNodeType(@Param("nodeType") String nodeType);
    
    /**
     * 根据处理人组代码查询节点
     */
    ProcessNode selectByHandlerGroupCode(@Param("handlerGroupCode") String handlerGroupCode);
    
    /**
     * 根据节点名称查询节点
     */
    ProcessNode selectByNodeName(@Param("nodeName") String nodeName);
    
    /**
     * 查询所有节点
     */
    List<ProcessNode> selectAll();
    
    /**
     * 更新节点配置
     */
    int update(ProcessNode node);
    
    /**
     * 删除节点配置
     */
    int deleteById(@Param("id") Integer id);
} 