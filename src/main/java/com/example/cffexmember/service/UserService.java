package com.example.cffexmember.service;

import com.example.cffexmember.entity.LoginUser;
import com.example.cffexmember.entity.User;
import com.example.cffexmember.mapper.UserMapper;
import com.example.cffexmember.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 用户服务类
 */
@Service
public class UserService {
    
    @Autowired
    private UserMapper userMapper;

    
    /**
     * 根据用户名查询用户
     */
    public LoginUser findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
    
    /**
     * 根据ID查询用户
     */
    public User findById(Integer id) {
        return userMapper.selectById(id);
    }
    
    /**
     * 根据用户组代码查询用户列表
     */
    public List<User> findByUsergroupCode(String usergroupCode) {
        return userMapper.selectByUsergroupCode(usergroupCode);
    }
    
    /**
     * 查询所有用户
     */
    public List<User> findAll() {
        return userMapper.selectAll();
    }

    
    /**
     * 删除用户
     */
    public int deleteUser(Integer id) {
        return userMapper.deleteById(id);
    }

    public Result userLogin(String username, String password,
                     HttpSession session,
                     HttpServletRequest request,
                     HttpServletResponse response){
        Result<LoginUser> result = new Result<>();
        LoginUser user = userMapper.findByUsername(username);
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

