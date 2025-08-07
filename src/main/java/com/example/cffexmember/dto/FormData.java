package com.example.cffexmember.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormData {
    private String memberName;
    private String shortName;
    private String englishName;
    private String englishShortName;
    private String memberType;
    private String clearingAgentName;
    private String address;
    private ContactInfo contact;
    private String companyIntro;
    private String name;    // 注意：可能与 contact.name 重复，确认是否必要
    private String phone;   // 同上
    private String email;   // 同上
}
