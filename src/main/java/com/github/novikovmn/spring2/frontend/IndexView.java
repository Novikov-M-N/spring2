package com.github.novikovmn.spring2.frontend;

import com.github.novikovmn.spring2.frontend.service.Page;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.Route;

@Route("index")
public class IndexView extends AbstractView{
    public IndexView() {
        initIndexPage();
    }

    private void initIndexPage() {
        showHeader(Page.INDEX);
        add(new Label("Здесь главная страница"));
    }
}
