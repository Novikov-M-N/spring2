package com.github.novikovmn.spring2.controller;

import com.github.novikovmn.spring2.domain.User;
import com.github.novikovmn.spring2.domain.dto.UserDto;
import com.github.novikovmn.spring2.domain.dto.UserType;
import com.github.novikovmn.spring2.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody @Valid UserDto userDto) {
        return userService.saveUser(userDto);
    }

    @GetMapping
    public List<User> getAllUsers(@RequestParam(value = "type", required = false)String type) {
        return userService.getAllUsersWithType(type);
    }
}
