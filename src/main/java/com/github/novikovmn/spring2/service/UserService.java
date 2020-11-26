package com.github.novikovmn.spring2.service;

import com.github.novikovmn.spring2.domain.Role;
import com.github.novikovmn.spring2.domain.User;
import com.github.novikovmn.spring2.domain.dto.UserDto;
import com.github.novikovmn.spring2.domain.dto.UserType;
import com.github.novikovmn.spring2.exception.ManagerIsEarlierThanNeedException;
import com.github.novikovmn.spring2.exception.UnknownUserTypeException;
import com.github.novikovmn.spring2.exception.UserTypeNotFoundException;
import com.github.novikovmn.spring2.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public User saveUser(UserDto userDto) {
        if (userDto.getUserType().equals(UserType.MANAGER)) {
            saveManager(userDto);
        } else if (userDto.getUserType().equals(UserType.CUSTOMER)) {
            saveTypicallyUser(userDto);
        }
        throw new UnknownUserTypeException();
    }

    private User saveTypicallyUser(UserDto userDto) {
        User user = createUserFromDto(userDto);

        Role role = roleService.getByName("ROLE_USER");
        user.setRoles(List.of(role));
        return userRepository.save(user);
    }

    private User saveManager(UserDto userDto) {
        if (userDto.getAge() > 18) {
            User user = createUserFromDto(userDto);
            Role role = roleService.getByName("ROLE_MANAGER");
            user.setRoles(List.of(role));
            return userRepository.save(user);
        }
        throw new ManagerIsEarlierThanNeedException("Пользователь младше 18 лет");
    }

    private User createUserFromDto(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setPhone(userDto.getPhone());
        user.setAge(userDto.getAge());

        return user;
    }

    public List<User> getAllUsersWithType(String role) {
        if (role == null) { return userRepository.findAll(); }
        try {
            UserType userType = UserType.valueOf(role);
            Role userRole = roleService.getByName(userType.getRole());
            return userRepository.findByRoles(userRole);
        } catch (IllegalArgumentException e) {
            throw new UserTypeNotFoundException(String.format("Тип пользователя %s не существует", role));
        }
    }
}