package com.example.cffexmember.service;

import com.example.cffexmember.model.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface UserService {

    Result userLogin(String username, String password,
                     HttpSession session,
                     HttpServletRequest request,
                     HttpServletResponse response);


}
