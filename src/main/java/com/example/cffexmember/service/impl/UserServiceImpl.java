package com.example.cffexmember.service.impl;

import com.example.cffexmember.entity.User;
import com.example.cffexmember.mapper.UserMapper;
import com.example.cffexmember.model.Result;
import com.example.cffexmember.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<User> userLogin(String username, String password,
                                  HttpSession session,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        Result<User> result = new Result<>();
        User user = userMapper.findByUsername(username);
        if (user == null) {
            result.setCode(-1);
            result.setMessage("用户名不存在");
            return result;
        } else if (!user.getPassword().equals(password)) {
            result.setCode(-1);
            result.setMessage("密码错误");
            return result;
        }
        // 将登录用户信息保存到session中
        session.setAttribute("userInfo", user);
        // 保存cookie，实现自动登录
        Cookie cookie = new Cookie("cookie", username);
        // expires in 30 minutes
        cookie.setMaxAge(30 * 60);
        // 设置为当前项目下都携带这个cookie
        cookie.setPath(request.getContextPath());
        // 向客户端发送cookie
        response.addCookie(cookie);
        result.setCode(0);
        result.setMessage("登录成功");
        result.setData(user);
        return result;
    }

}

