package com.github.novikovmn.spring2.frontend.service;

public enum Page {
    INDEX("Главная","index", false, true),
    MARKET("Магазин","market", false, true),
    CART("Корзина","cart", false, true),
    ORDERS("Мои заказы","orders", true, true),
    ACCOUNT("Аккаунт", "account", false, true),
    DEPOSIT("Пополнить","deposit", true, false),
    LOGOUT("Выйти","logout", true, false),
    NONE("","",false,true);
    ;

    private final String title;
    private final String location;
    private final boolean needAuthentication;
    private final boolean showForAll;

    public String getTitle() { return title; }
    public String getLocation() { return location; }
    public boolean isNeedAuthentication() { return needAuthentication; }
    public boolean isShowForAll() { return showForAll; }

    Page(String title, String location, boolean needAuthentication, boolean showForAll) {
        this.title = title;
        this.location = location;
        this.needAuthentication = needAuthentication;
        this.showForAll = showForAll;
    }
}
