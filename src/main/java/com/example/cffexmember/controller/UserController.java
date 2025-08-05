package com.example.cffexmember.controller;

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
@RequestMapping
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/login")
    public Result login(@RequestParam String username, @RequestParam String password,
                        HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        log.info("Login request received: username={}", username);
        System.out.println(">>>>>> 进入 /login 控制器 <<<<<<");
        return userService.userLogin(username, password, session, request, response);
    }
}
