package com.example.cffexmember.mapper;

import com.example.cffexmember.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户Mapper接口
 */
@Mapper
public interface UserMapper {
    
    /**
     * 插入用户
     */
    int insert(User user);
    
    /**
     * 根据ID查询用户
     */
    User selectById(@Param("id") Integer id);
    
    /**
     * 根据用户名查询用户
     */
    User selectByUsername(@Param("username") String username);
    
    /**
     * 根据用户组代码查询用户列表
     */
    List<User> selectByUsergroupCode(@Param("usergroupCode") String usergroupCode);
    
    /**
     * 查询所有用户
     */
    List<User> selectAll();
    
    /**
     * 更新用户信息
     */
    int update(User user);
    
    /**
     * 删除用户
     */
    int deleteById(@Param("id") Integer id);
} 