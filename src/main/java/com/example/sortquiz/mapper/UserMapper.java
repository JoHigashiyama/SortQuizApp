package com.example.sortquiz.mapper;

import com.example.sortquiz.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT user_id, email, username, password, created_at, updated_at FROM users WHERE email = #{email}")
    User selectUserByEmail(String email);

    @Select("SELECT user_id, email, username, password, created_at, updated_at FROM users WHERE username = #{username}")
    User selectUserByUsername(String username);

    @Insert("INSERT INTO users (username, email, password) VALUES (#{username}, #{email}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    void insertUser(User user);

    @Select("SELECT * FROM users WHERE user_id = #{userId}")
    User getUserInformation(long userId);

    @Update("UPDATE users SET best_score = #{bestScore}, total_score = #{totalScore} WHERE user_id = #{userId}")
    void updateUserScoreByUserId(long bestScore, long totalScore, long userId);
}
