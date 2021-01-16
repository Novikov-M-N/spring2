package com.github.novikovmn.spring2.domain.dto;

import com.github.novikovmn.spring2.domain.Role;
import com.github.novikovmn.spring2.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
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
    private List<RoleDto> roles;

    public UserDto(User template) {
        this.phone = template.getPhone();
        this.password = template.getPassword();
        this.firstName = template.getFirstName();
        this.lastName = template.getLastName();
        this.email = template.getEmail();
        this.age = template.getAge();
        this.roles = new ArrayList<>();
        for (Role role : template.getRoles()) {
            this.roles.add(RoleDto.valueOf(role.getName()));
        }
    }
}