package com.example.cffexmember.controller;

import com.example.cffexmember.entity.ApplicationAttachment;
import com.example.cffexmember.model.Result;
import com.example.cffexmember.service.ApplicationAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class AttachmentController {

    @Autowired
    private ApplicationAttachmentService applicationAttachmentService;

    @Value("${file.upload.url}")
    private String uploadFilePath;

    @PostMapping("/uploadfile")
    public Result<?> uploadFile(@RequestParam("file") MultipartFile file) {
        ApplicationAttachment attachment = applicationAttachmentService.saveAttachment(file);
        Result result = new Result();
        result.setCode(0);
        result.setMessage("文件上传成功");
        result.setData(attachment);
        return result;
    }

}
