package com.example.sortquiz.controller;

import com.example.sortquiz.service.UserService;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


}
