package com.example.sortquiz.service;

import com.example.sortquiz.entity.User;
import com.example.sortquiz.repository.UserRepository;
import com.example.sortquiz.viewModel.UserViewModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserViewModel> getUserInformation(){
        return userRepository.getUserInformation();
    }
}
