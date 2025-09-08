package com.example.sortquiz.service;

import com.example.sortquiz.entity.User;
import com.example.sortquiz.exception.EmailAlreadyRegisteredException;
import com.example.sortquiz.exception.PasswordNotMatchException;
import com.example.sortquiz.exception.UsernameAlreadyRegisteredException;
import com.example.sortquiz.form.UserForm;
import com.example.sortquiz.repository.UserRepository;
import com.example.sortquiz.viewModel.UserViewModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(UserForm userForm) {
//        ユーザー名が登録済みか
        User existingUsername = userRepository.selectUserByUsername(userForm.getUsername());
        if (existingUsername != null) {
            throw new UsernameAlreadyRegisteredException("Username is already registered.");
        }

//        メールだドレスがすでに登録済みか
        User existingEmail = userRepository.selectUserByEmail(userForm.getEmail());
        if (existingEmail != null) {
            throw new EmailAlreadyRegisteredException("Email is already registered.");
        }

//        パスワードが確認用と一致しているか
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            throw new PasswordNotMatchException("Password is not matched.");
        }

        User user = new User();
        user.setUsername(userForm.getUsername());
        user.setEmail(userForm.getEmail());
        String hashedPassword = passwordEncoder.encode(userForm.getPassword());
        user.setPassword(hashedPassword);
        userRepository.insertUser(user);
    }

    public List<UserViewModel> getUserInformation(){
        return userRepository.getUserInformation();
    }
}
