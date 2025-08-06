package com.example.cffexmember.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class ApplicationRecord {
    int applicationId;
    String memberName;
    String status;
    String submitAt;
}
