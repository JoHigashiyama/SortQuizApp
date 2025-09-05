package com.example.sortquiz.service;

import com.example.sortquiz.entity.User;
import com.example.sortquiz.form.UserForm;
import com.example.sortquiz.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean createUser(UserForm userForm) {
        if (userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            return false;
        }
        User user = new User();
        user.setUsername(userForm.getUsername());
        user.setEmail(userForm.getEmail());
        String hashedPassword = passwordEncoder.encode(userForm.getPassword());
        user.setPassword(hashedPassword);
        userRepository.insertUser(user);
        return true;
    }
}
