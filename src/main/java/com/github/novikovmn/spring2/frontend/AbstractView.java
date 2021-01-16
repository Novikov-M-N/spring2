package com.github.novikovmn.spring2.frontend;

import com.github.novikovmn.spring2.domain.User;
import com.github.novikovmn.spring2.domain.dto.RoleDto;
import com.github.novikovmn.spring2.frontend.service.Page;
import com.github.novikovmn.spring2.frontend.service.custom_components.LabelWithValue;
import com.github.novikovmn.spring2.security.SecurityUtils;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class AbstractView extends VerticalLayout {
    protected final User user;
    protected final Authentication authentication;

    protected AbstractView() {
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isNotAuthenticated()) {
            this.user = null;
        } else {
            this.user = SecurityUtils.getPrincipal().getUser();
        }
    }

    protected void showHeader(Page page) {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        HorizontalLayout layout = new HorizontalLayout();
        layout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        for (Page pageHeader: Page.values()) {
            if (pageHeader == Page.NONE) { continue; }
            Button button = new Button(pageHeader.getTitle(), e -> {
                if (pageHeader == Page.LOGOUT) {
                    SecurityContextHolder.clearContext();
                }
                if (pageHeader == Page.ACCOUNT && isNotAuthenticated()) {
                    UI.getCurrent().navigate("login");
                    return;
                }
                if (pageHeader.isNeedAuthentication() && isNotAuthenticated()) {
                    Notification.show("Доступно только для зарегистрированных пользователей");
                    Notification.show("Воспользуйтесь кнопкой [Войти]");
                    return;
                }
                UI.getCurrent().navigate(pageHeader.getLocation());
            });
            if (pageHeader == page) {
                button.setEnabled(false);
            }
            if (pageHeader == Page.ACCOUNT) {
                layout.add(new Label("Личный кабинет: "));
                if (isAuthenticated()) { button.setText(user.getPhone()); }
                else { button.setText("Войти"); }
            }
            if (pageHeader == Page.DEPOSIT) {
                if (isAuthenticated()) {
                    layout.add(new LabelWithValue<>("Баланс: ", " р.", user.getBalance()));
                }
            }
            if (isAuthenticated() || pageHeader.isShowForAll()) { layout.add(button); }
        }
        add(layout);
    }

    protected boolean isAuthenticated() {
        if (authentication == null) {
            return false;
        }
        return authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ANONYMOUS"));
    }

    protected boolean isNotAuthenticated() {
        return !isAuthenticated();
    }

    protected boolean isCustomer() {
        return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(RoleDto.CUSTOMER.name()));
    }

    protected boolean isManager() {
        return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(RoleDto.MANAGER.name()));
    }

    protected boolean isAdmin() {
        return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(RoleDto.ADMIN.name()));
    }
}
