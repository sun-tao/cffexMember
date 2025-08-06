package com.example.cffexmember.mapper;

import com.example.cffexmember.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User findByUsername(String username);

}
