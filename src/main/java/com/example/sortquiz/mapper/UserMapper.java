package com.example.sortquiz.mapper;

import com.example.sortquiz.entity.User;
import com.example.sortquiz.viewModel.UserViewModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT user_id, email, username, password, created_at, updated_at FROM users WHERE email = #{email}")
    User selectUserByEmail(String email);

    @Select("SELECT user_id, email, username, password, created_at, updated_at FROM users WHERE username = #{username}")
    User selectUserByUsername(String username);

    //    トップ画面
    @Select("SELECT * FROM users WHERE user_id = #{userId}")
    List<UserViewModel> getUserInformation();
}
