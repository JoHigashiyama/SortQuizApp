package com.example.sortquiz.handler;

import com.example.sortquiz.exception.EmailAlreadyRegisteredException;
import com.example.sortquiz.exception.PasswordNotMatchException;
import com.example.sortquiz.exception.UsernameAlreadyRegisteredException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(UsernameAlreadyRegisteredException.class)
    public String handleUsernameAlreadyRegisteredException(
            UsernameAlreadyRegisteredException e,
            RedirectAttributes redirectAttributes
    ) {
        redirectAttributes.addFlashAttribute("usernameError", "すでに使われているユーザー名です");
        return "redirect:/register";
    }

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public String handleEmailAlreadyRegisteredException(
            EmailAlreadyRegisteredException e,
            RedirectAttributes redirectAttributes
    ) {
        redirectAttributes.addFlashAttribute("emailError", "すでに使われているメールアドレスです");
        return "redirect:/register";
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public String handlePasswordNotMatchException(
            PasswordNotMatchException e,
            RedirectAttributes redirectAttributes
    ) {
        redirectAttributes.addFlashAttribute("passwordError", "パスワードが一致しませんでした");
        return "redirect:/register";
    }
}
