//package com.example.cffexmember.util;
//
//import com.example.cffexmember.entity.User;
//import com.example.cffexmember.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.stereotype.Component;
//
///**
// * 安全工具类
// */
//@Component
//public class SecurityUtil {
//
//    @Autowired
//    private UserService userService;
//
//    /**
//     * 获取当前登录用户
//     */
//    public User getCurrentUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return null;
//        }
//
//        String username = authentication.getName();
//        return userService.findByUsername(username);
//    }
//
//    /**
//     * 获取当前用户ID
//     */
//    public Integer getCurrentUserId() {
//        User user = getCurrentUser();
//        return user != null ? user.getId() : null;
//    }
//
//    /**
//     * 获取当前用户名
//     */
//    public String getCurrentUsername() {
//        User user = getCurrentUser();
//        return user != null ? user.getUsername() : null;
//    }
//
//    /**
//     * 获取当前用户组代码
//     */
//    public String getCurrentUserGroupCode() {
//        User user = getCurrentUser();
//        return user != null ? user.getUsergroupCode() : null;
//    }
//
//    /**
//     * 检查当前用户是否已登录
//     */
//    public boolean isAuthenticated() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return authentication != null && authentication.isAuthenticated();
//    }
//
//    /**
//     * 检查当前用户是否属于指定用户组
//     */
//    public boolean hasUserGroup(String userGroupCode) {
//        User user = getCurrentUser();
//        return user != null && userGroupCode.equals(user.getUsergroupCode());
//    }
//}