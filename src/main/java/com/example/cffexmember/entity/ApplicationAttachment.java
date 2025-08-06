package com.example.cffexmember.entity;

/**
 * 申请附件实体类
 */
public class ApplicationAttachment {
    private Integer id;
    private String fileName;
    private String storagePath;
    private String downloadUrl;

    // 构造函数
    public ApplicationAttachment() {}

    public ApplicationAttachment(String fileName, String storagePath, String downloadUrl) {
        this.fileName = fileName;
        this.storagePath = storagePath;
        this.downloadUrl = downloadUrl;
    }

    // Getter和Setter方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @Override
    public String toString() {
        return "ApplicationAttachment{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", storagePath='" + storagePath + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
    }
} 