package com.example.cffexmember.service;

import com.example.cffexmember.entity.ProcessNode;
import com.example.cffexmember.mapper.ProcessNodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 流程节点服务类
 */
@Service
public class ProcessNodeService {
    
    @Autowired
    private ProcessNodeMapper processNodeMapper;
    
    /**
     * 根据处理人组代码获取节点名称
     */
    public String getNodeNameByHandlerGroupCode(String handlerGroupCode) {
        ProcessNode node = processNodeMapper.selectByHandlerGroupCode(handlerGroupCode);
        return node != null ? node.getNodeName() : null;
    }
    
    /**
     * 根据节点名称获取节点信息
     */
    public ProcessNode getNodeByNodeName(String nodeName) {
        return processNodeMapper.selectByNodeName(nodeName);
    }
    
    /**
     * 根据ID获取节点信息
     */
    public ProcessNode getNodeById(Integer id) {
        return processNodeMapper.selectById(id);
    }
    
    /**
     * 根据节点类型获取节点列表
     */
    public List<ProcessNode> getNodesByNodeType(String nodeType) {
        return processNodeMapper.selectByNodeType(nodeType);
    }
    
    /**
     * 获取所有节点
     */
    public List<ProcessNode> getAllNodes() {
        return processNodeMapper.selectAll();
    }
    
    /**
     * 保存节点配置
     */
    public boolean saveNode(ProcessNode node) {
        if (node.getId() == null) {
            return processNodeMapper.insert(node) > 0;
        } else {
            return processNodeMapper.update(node) > 0;
        }
    }
    
    /**
     * 删除节点配置
     */
    public boolean deleteNode(Integer id) {
        return processNodeMapper.deleteById(id) > 0;
    }
} 