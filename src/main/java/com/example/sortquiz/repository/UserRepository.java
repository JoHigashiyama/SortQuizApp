package com.example.sortquiz.repository;

import com.example.sortquiz.entity.User;
import com.example.sortquiz.mapper.UserMapper;
import com.example.sortquiz.viewModel.UserViewModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {
    private final UserMapper userMapper;

    public UserRepository(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User selectUserByEmail(String email) {
        return userMapper.selectUserByEmail(email);
    }

    public User selectUserByUsername(String username) {
        return userMapper.selectUserByUsername(username);
    }

    public List<UserViewModel> getUserInformation() {
        return userMapper.getUserInformation();
    }

    public void insertUser(User user) {
        userMapper.insertUser(user);

    }
}
