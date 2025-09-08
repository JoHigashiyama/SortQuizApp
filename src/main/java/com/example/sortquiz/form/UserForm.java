package com.example.sortquiz.form;

import lombok.Data;

@Data
public class UserForm {
    private String username;
    private String email;
    private String password;
    private String passwordConfirm;
}
