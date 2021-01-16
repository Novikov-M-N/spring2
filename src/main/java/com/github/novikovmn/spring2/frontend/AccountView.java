package com.github.novikovmn.spring2.frontend;

import com.github.novikovmn.spring2.frontend.service.Page;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.Route;

@Route("account")
public class AccountView extends AbstractView{
    public AccountView() {
        initAccountPage();
    }

    private void initAccountPage() {
        showHeader(Page.ACCOUNT);
        add(new Label("Здесь личный кабинет"));
    }
}
