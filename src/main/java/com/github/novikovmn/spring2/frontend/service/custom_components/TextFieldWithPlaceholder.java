package com.github.novikovmn.spring2.frontend.service.custom_components;

import com.vaadin.flow.component.textfield.TextField;

public class TextFieldWithPlaceholder extends TextField {
    public TextFieldWithPlaceholder(String placeholder) {
        this.setPlaceholder(placeholder);
    }
}
