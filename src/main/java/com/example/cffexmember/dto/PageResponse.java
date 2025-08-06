package com.example.cffexmember.dto;

import lombok.Data;
import java.util.List;

/**
 * 分页响应DTO
 */
@Data
public class PageResponse<T> {
    private List<T> content;
    private int page;
    private int pageSize;
    private long total;
    private int totalPages;
    
    public PageResponse() {}
    
    public PageResponse(List<T> content, int page, int pageSize, long total) {
        this.content = content;
        this.page = page;
        this.pageSize = pageSize;
        this.total = total;
        this.totalPages = (int) Math.ceil((double) total / pageSize);
    }
} 