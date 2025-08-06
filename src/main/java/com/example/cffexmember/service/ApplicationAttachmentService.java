package com.example.cffexmember.service;

import com.example.cffexmember.entity.ApplicationAttachment;
import com.example.cffexmember.mapper.ApplicationAttachmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 申请附件服务类
 */
@Service
public class ApplicationAttachmentService {

    @Value("${file.upload-dir}")
    private String uploadFilePath;
    
    @Autowired
    private ApplicationAttachmentMapper attachmentMapper;
    
    /**
     * 根据ID查询附件
     */
    public ApplicationAttachment getById(Integer id) {
        return attachmentMapper.selectById(id);
    }
    
    /**
     * 根据申请ID查询附件列表
     */
    public List<ApplicationAttachment> getByApplicationId(Integer applicationId) {
        return attachmentMapper.selectByApplicationId(applicationId);
    }
    
    /**
     * 创建附件
     */
    public ApplicationAttachment createAttachment(ApplicationAttachment attachment) {
        attachmentMapper.insert(attachment);
        return attachment;
    }
    
    /**
     * 更新附件
     */
    public int updateAttachment(ApplicationAttachment attachment) {
        return attachmentMapper.update(attachment);
    }
    
    /**
     * 删除附件
     */
    public int deleteAttachment(Integer id) {
        return attachmentMapper.deleteById(id);
    }
    
    /**
     * 查询所有附件
     */
    public List<ApplicationAttachment> getAllAttachments() {
        return attachmentMapper.selectAll();
    }

    public ApplicationAttachment saveAttachment(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String storagePath = uploadFilePath + fileName;
        try {
            file.transferTo(new File(storagePath));
            ApplicationAttachment attachment = new ApplicationAttachment();
            attachment.setFileName(fileName);
            attachment.setStoragePath(storagePath);
            attachment.setDownloadUrl("");
            attachmentMapper.insert(attachment);
            return attachment;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + fileName, e);
        }
    }
} 