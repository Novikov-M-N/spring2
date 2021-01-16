package com.github.novikovmn.spring2.frontend.service.custom_components;

import com.vaadin.flow.component.html.Label;

public class LabelWithValue<T> extends Label {
    private String prefix;
    private String suffix;

    public LabelWithValue(String prefix, String suffix, T value) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.setText(prefix + value + suffix);
    }

    public void setValue(T value) {
        this.setText(prefix + value + suffix);
    }

    public void setPrefix(String prefix) { this.prefix = prefix; }
    public void setSuffix(String suffix) { this.suffix = suffix; }
}
