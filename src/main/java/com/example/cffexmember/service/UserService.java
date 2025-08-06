package com.example.cffexmember.service;

import com.example.cffexmember.entity.User;
import com.example.cffexmember.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public User findByUsername(String username) {
        return userMapper.selectByUsername(username);
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

}
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
