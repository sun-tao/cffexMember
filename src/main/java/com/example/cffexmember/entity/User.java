package com.example.cffexmember.entity;

/**
 * 用户实体类
 */
public class User {
    private Integer id;
    private String username;
    private String usergroupCode;
    private String usergroupName;

    // 构造函数
    public User() {}

    public User(String username, String usergroupCode, String usergroupName) {
        this.username = username;
        this.usergroupCode = usergroupCode;
        this.usergroupName = usergroupName;
    }

    // Getter和Setter方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsergroupCode() {
        return usergroupCode;
    }

    public void setUsergroupCode(String usergroupCode) {
        this.usergroupCode = usergroupCode;
    }

    public String getUsergroupName() {
        return usergroupName;
    }

    public void setUsergroupName(String usergroupName) {
        this.usergroupName = usergroupName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", usergroupCode='" + usergroupCode + '\'' +
                ", usergroupName='" + usergroupName + '\'' +
                '}';
    }
} 