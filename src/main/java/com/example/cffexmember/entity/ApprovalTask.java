package com.example.cffexmember.entity;

import java.util.Date;

/**
 * 审批任务实体类
 */
public class ApprovalTask {
    private Integer id;
    private Integer applicationId;
    private Integer nodeId;
    private String nodeName;
    private String taskStatus; // PENDING, COMPLETED
    private Date ctime;
    private Date mtime;

    // 构造函数
    public ApprovalTask() {}

    public ApprovalTask(Integer applicationId, Integer nodeId, String nodeName) {
        this.applicationId = applicationId;
        this.nodeId = nodeId;
        this.nodeName = nodeName;
        this.taskStatus = "PENDING";
        this.ctime = new Date();
        this.mtime = new Date();
    }

    // Getter和Setter方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    @Override
    public String toString() {
        return "ApprovalTask{" +
                "id=" + id +
                ", applicationId=" + applicationId +
                ", nodeId=" + nodeId +
                ", nodeName='" + nodeName + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                ", ctime=" + ctime +
                ", mtime=" + mtime +
                '}';
    }
} 