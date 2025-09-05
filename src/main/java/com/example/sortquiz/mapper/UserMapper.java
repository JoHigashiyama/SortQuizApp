package com.example.sortquiz.mapper;

import com.example.sortquiz.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT user_id, email, username, password, created_at, updated_at FROM users WHERE email = #{email}")
    User selectUserByEmail(String email);

    @Select("SELECT user_id, email, username, password, created_at, updated_at FROM users WHERE username = #{username}")
    User selectUserByUsername(String username);
}
