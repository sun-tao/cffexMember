package com.example.cffexmember.controller;

import com.example.cffexmember.entity.User;
import com.example.cffexmember.service.UserService;
import com.example.cffexmember.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;


@RestController
@RequestMapping("/")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    @PostMapping("/login")
    public Result login(@RequestParam String username, @RequestParam String password,
                        HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        log.info("用户登录");
        Result result = userService.userLogin(username, password, session, request, response);
        if (result != null && result.getData() != null) {
//            java.util.Map<String, Object> resultData = (java.util.Map<String, Object>) result.getData();
            User user = (User) result.getData();
            String userType = user.getUsergroupCode();
            if (!"member".equals(user.getUsergroupCode())){
                userType = "admin";
            }
            String department = "";
            String displayName = "";
//            Object permissions = resultData.getOrDefault("permissions", null);

            java.util.Map<String, Object> userInfo = new java.util.HashMap<>();
            userInfo.put("id", user.getId());
            userInfo.put("username", username);
            userInfo.put("userType", userType);
            userInfo.put("department", department);
            userInfo.put("displayName", displayName);
//            userInfo.put("permissions", permissions);

            java.util.Map<String, Object> data = new java.util.HashMap<>();
            data.put("token", "mock_token_" + System.currentTimeMillis());
            data.put("userInfo", userInfo);

            result.setData(data);
        }
        return result;
    }
}
