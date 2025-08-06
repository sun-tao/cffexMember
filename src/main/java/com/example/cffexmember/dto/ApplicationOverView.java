package com.example.cffexmember.dto;

import com.example.cffexmember.entity.ApplicationRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationOverView {
    private List<ApplicationRecord> applicationRecord;
    private int pageNo;
    private int pageSize;
    int total;
    int totalPage;
}
