package com.github.novikovmn.spring2.frontend;

import com.github.novikovmn.spring2.frontend.AbstractView;
import com.github.novikovmn.spring2.frontend.service.Page;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("logout")
public class LogoutView extends AbstractView {
    public LogoutView() {
        showHeader(Page.LOGOUT);
        initLogoutPage();
    }

    private void initLogoutPage() {
        add(new Label("Вы вышли"));
    }
}
