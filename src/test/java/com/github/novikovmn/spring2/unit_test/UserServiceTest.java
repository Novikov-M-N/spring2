package com.github.novikovmn.spring2.unit_test;

import com.github.novikovmn.spring2.domain.Role;
import com.github.novikovmn.spring2.domain.User;
import com.github.novikovmn.spring2.domain.dto.RoleDto;
import com.github.novikovmn.spring2.domain.dto.UserDto;
import com.github.novikovmn.spring2.exception.UserNotFoundException;
import com.github.novikovmn.spring2.exception.UserTypeNotFoundException;
import com.github.novikovmn.spring2.repository.UserRepository;
import com.github.novikovmn.spring2.service.RoleService;
import com.github.novikovmn.spring2.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService testUserService;

    @MockBean
    private UserRepository testUserRepository;

    @MockBean
    private RoleService TestRoleService;

    private List<Role> roles;
    private List<User> users;
    private List<UserDto> userDtoList;

    @BeforeEach
    public void initData() {
        roles = new ArrayList<>();
        users = new ArrayList<>();
        userDtoList = new ArrayList<>();
        long i = 100L;
        /**
         * Подготовка тестовых данных: по одному пользователю каждой роли, кроме ADMIN (создаётся вручную через БД)
         */
        for (RoleDto roleDto : RoleDto.values()) {
            if (roleDto == RoleDto.ADMIN) { continue; }
            i++;
            Role role = new Role();
            role.setId(i);
            role.setName(roleDto.name());
            roles.add(role);

            String roleName = role.getName();
            User user = new User();
            user.setPhone(roleName + " user phone");
            user.setPassword(roleName + " user password");
            user.setFirstName(roleName + " user firstname");
            user.setLastName(roleName + " user lastname");
            user.setEmail(roleName + " user email");
            user.setAge(20);
            user.setRoles(new ArrayList<>(Arrays.asList(new Role[]{role})));
            users.add(user);
            UserDto userDto = new UserDto(user);
            userDtoList.add(userDto);
        }

        /**
         * Поведение для заглушки RoleService
         */
        for (Role role : roles) {
            Mockito.doReturn(role)
                    .when(TestRoleService)
                    .getByName(role.getName());
        }

        /**
         * Поведение для заглушки UserRepository
         */
        for (User user : users) {
            Mockito.doReturn(user)
                    .when(testUserRepository)
                    .save(user);
        }

        Mockito.doReturn(users)
                .when(testUserRepository)
                .findAll();

        for (Role role : roles) {
            Mockito.doReturn(findAllByRole(role))
                    .when(testUserRepository)
                    .findAllByRoles(role);
        }
    }

    @Test
    public void saveUserTest() {
        for (UserDto userDto : userDtoList) {
            User savedUser = testUserService.saveUser(userDto);
            User user = users.get(userDtoList.indexOf(userDto));
            Assertions.assertEquals(user, savedUser);
            Mockito.verify(testUserRepository,
                    Mockito.times(1)).save(ArgumentMatchers.eq(user));
        }

    }

    @Test
    public void getAllUsersWithTypeTest() {
        /**
         * Роль не указана - возвращает всех пользователей
         */
        List<User> gettedUsers = testUserService.getAllUsersWithType(null);
        Assertions.assertEquals(users, gettedUsers);
        Mockito.verify(testUserRepository,
                Mockito.times(1)).findAll();
        /**
         * Роль из исходного списка - возвращает всех пользователей с данной ролью
         */
        for (Role role : roles) {
            gettedUsers = testUserService.getAllUsersWithType(role.getName());
            Assertions.assertEquals(findAllByRole(role), gettedUsers);
            Mockito.verify(testUserRepository,
                    Mockito.times(1)).findAllByRoles(ArgumentMatchers.eq(role));
        }
        /**
         * Не существующая роль - тестируемый метод возвращает исключение, не использует репозиторий
         */
        Role wrongRole = new Role();
        wrongRole.setId(1001L);
        wrongRole.setName("ROLE_DRACULA");
        Assertions.assertThrows(UserTypeNotFoundException.class,
                () -> testUserService.getAllUsersWithType(wrongRole.getName()));
        Mockito.verify(testUserRepository,
                Mockito.times(0)).findAllByRoles(ArgumentMatchers.eq(wrongRole));
    }

    @Test
    public void getByIdTest() {
        for (User user : users) {
            /**
             * Подготовка данных - присвоение id тестовым пользователям
             * и определение поведения заглушки UserRepository для поиска по id
             */
            long id = 100L + users.indexOf(user);
            user.setId(id);
            Mockito.doReturn(Optional.of(user))
                    .when(testUserRepository)
                    .findById(id);

            User gettedUser = testUserService.getById(id);
            Assertions.assertEquals(gettedUser, user);
            Mockito.verify(testUserRepository,
                    Mockito.times(1)).findById(ArgumentMatchers.eq(id));
        }
        /**
         * Некорректный пользователь - тестируемый метод возвращает исключение, обращается к репозиторию
         */
        User wrongUser = new User();
        long wrongUserId = 1001L;
        wrongUser.setId(wrongUserId);
        Mockito.doReturn(Optional.empty())
                .when(testUserRepository)
                .findById(wrongUserId);
        Assertions.assertThrows(UserNotFoundException.class, () -> testUserService.getById(wrongUserId));
        Mockito.verify(testUserRepository,
                Mockito.times(1)).findById(ArgumentMatchers.eq(wrongUserId));
    }

    /**
     * Вспомогательная функция для заглушки UserRepository - поиск пользователя по роли
     * @param role - искомая роль
     * @return - список пользователей, имеющих роль role
     */
    private List<User> findAllByRole(Role role) {
        List<User> result = new ArrayList<>();
        for (User user : users) {
            if (user.getRoles().contains(role)) { result.add(user); }
        }
        return result;
    }
}
