package com.confluenciacreativa.fundamentos.caseuse;

import com.confluenciacreativa.fundamentos.entity.User;
import com.confluenciacreativa.fundamentos.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class CreateUser {
    private UserService userService;

    public CreateUser(UserService userService) {
        this.userService = userService;
    }

    public User save(User newUser) {
        return userService.save(newUser);
    }
}
