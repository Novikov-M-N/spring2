package com.github.novikovmn.spring2.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserDto {

    @NotNull(message = "Телефон должен быть указан")
    private String phone;
    @NotNull(message = "Пароль должен быть указан")
    private String password;
    @NotNull(message = "Имя должно быть указано")
    private String firstName;
    @NotNull(message = "Фамилия должна быть указана")
    private String lastName;
    @NotNull(message = "Электронная почта должна быть указана")
    private String email;
    @NotNull(message = "Возраст должен быть указан")
    private Integer age;
    @NotNull(message = "Тип пользователя должен быть указан")
    private UserType userType;
}
