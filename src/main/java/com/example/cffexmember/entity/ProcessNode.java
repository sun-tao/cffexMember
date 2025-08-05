package com.example.cffexmember.entity;

/**
 * 流程节点配置实体类
 */
public class ProcessNode {
    private Integer id;
    private String nodeName;
    private String nodeType; // START, PROCESSING, END
    private String handlerGroupCode;
    private Integer onApproveNodeId;
    private Integer onRejectNodeId;
    private Integer onReturnNodeId;

    // 构造函数
    public ProcessNode() {}

    public ProcessNode(String nodeName, String nodeType, String handlerGroupCode) {
        this.nodeName = nodeName;
        this.nodeType = nodeType;
        this.handlerGroupCode = handlerGroupCode;
    }

    // Getter和Setter方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getHandlerGroupCode() {
        return handlerGroupCode;
    }

    public void setHandlerGroupCode(String handlerGroupCode) {
        this.handlerGroupCode = handlerGroupCode;
    }

    public Integer getOnApproveNodeId() {
        return onApproveNodeId;
    }

    public void setOnApproveNodeId(Integer onApproveNodeId) {
        this.onApproveNodeId = onApproveNodeId;
    }

    public Integer getOnRejectNodeId() {
        return onRejectNodeId;
    }

    public void setOnRejectNodeId(Integer onRejectNodeId) {
        this.onRejectNodeId = onRejectNodeId;
    }

    public Integer getOnReturnNodeId() {
        return onReturnNodeId;
    }

    public void setOnReturnNodeId(Integer onReturnNodeId) {
        this.onReturnNodeId = onReturnNodeId;
    }

    @Override
    public String toString() {
        return "ProcessNode{" +
                "id=" + id +
                ", nodeName='" + nodeName + '\'' +
                ", nodeType='" + nodeType + '\'' +
                ", handlerGroupCode='" + handlerGroupCode + '\'' +
                ", onApproveNodeId=" + onApproveNodeId +
                ", onRejectNodeId=" + onRejectNodeId +
                ", onReturnNodeId=" + onReturnNodeId +
                '}';
    }
} 