package com.github.novikovmn.spring2.frontend;

import com.github.novikovmn.spring2.domain.User;
import com.github.novikovmn.spring2.domain.dto.RoleDto;
import com.github.novikovmn.spring2.domain.dto.UserDto;
import com.github.novikovmn.spring2.frontend.service.Page;
import com.github.novikovmn.spring2.frontend.service.custom_components.TextFieldWithPlaceholder;
import com.github.novikovmn.spring2.security.SecurityUtils;
import com.github.novikovmn.spring2.service.UserService;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route("registration")
public class RegistrationView extends AbstractView{
    private final UserService userService;

    public RegistrationView(UserService userService) {
        this.userService = userService;
        showHeader(Page.NONE);
        initRegistrationPage();
    }

    private void initRegistrationPage() {
        IntegerField phoneField = new IntegerField();
        phoneField.setPlaceholder("Номер телефона");
        widthCalibration(phoneField);
        add(phoneField);
        PasswordField passwordField = new PasswordField();
        passwordField.setPlaceholder("Пароль");
        widthCalibration(passwordField);
        add(passwordField);
        PasswordField passwordConfirmField = new PasswordField();
        passwordConfirmField.setPlaceholder("Подтверждение пароля");
        widthCalibration(passwordConfirmField);
        add(passwordConfirmField);
        TextField firstNameField = new TextFieldWithPlaceholder("Имя");
        widthCalibration(firstNameField);
        TextField lastNameField = new TextFieldWithPlaceholder("Фамилия");
        widthCalibration(lastNameField);
        TextField emailField = new TextFieldWithPlaceholder("электронная почта");
        widthCalibration(emailField);
        add(firstNameField, lastNameField, emailField);
        IntegerField ageField = new IntegerField();
        ageField.setPlaceholder("Возраст");
        widthCalibration(ageField);
        add(ageField);
        Button submitButton = new Button("Зарегистрироваться", e -> {
            boolean hasError = false;
            if (phoneField.isEmpty()) {
                Notification.show("Номер телефона должен быть указан");
                hasError = true;
            }
            if (passwordField.isEmpty()) {
                Notification.show("Пароль и подтверждение должны быть указаны");
                hasError = true;
            }
            if (!passwordConfirmField.getValue().equals(passwordField.getValue())) {
                Notification.show("Пароль и подтверждение должны совпадать");
                hasError = true;
            }
            if (!firstNameField.getValue().matches("[а-яА-Я]+")) {
                Notification.show("Имя должно состоять из букв");
                hasError = true;
            }
            if (!lastNameField.getValue().matches("[а-яА-Я]+")) {
                Notification.show("Фамилия должна состоять из букв");
                hasError = true;
            }
            if (emailField.isEmpty()) {
                Notification.show("Адрес электронной почты должен быть указан");
                hasError = true;
            }
            if (ageField.isEmpty()) {
                Notification.show("Возраст должен быть указан");
                hasError = true;
            }
            if (ageField.getValue() < 18) {
                Notification.show("Возраст должен быть не меньше 18 лет");
                hasError = true;
            }
            if (hasError) { return; }
            List<RoleDto> roles = new ArrayList<>();
            roles.add(RoleDto.CUSTOMER);
            UserDto userDto = new UserDto(
                    phoneField.getValue().toString(),
                    SecurityUtils.encodePassword(passwordField.getValue()),
                    firstNameField.getValue(),
                    lastNameField.getValue(),
                    emailField.getValue(),
                    ageField.getValue(),
                    roles
            );
            User user = userService.saveUser(userDto);
            if (user != null) {
                Notification.show("Пользователь " + user.getPhone() + " успешно зарегистрирован");
                UI.getCurrent().navigate("login");
            }
        });
        add(submitButton);
    }

    private <T extends HasSize> void widthCalibration(T field) {
        int TEXT_FIELD_WIDTH = 20;
        field.setWidth(TEXT_FIELD_WIDTH, Unit.EM);
    }
}
