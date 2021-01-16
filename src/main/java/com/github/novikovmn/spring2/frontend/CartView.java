package com.github.novikovmn.spring2.frontend;

import com.github.novikovmn.spring2.domain.OrderItem;
import com.github.novikovmn.spring2.frontend.service.Page;
import com.github.novikovmn.spring2.frontend.service.custom_components.LabelWithValue;
import com.github.novikovmn.spring2.frontend.service.custom_components.TextFieldWithPlaceholder;
import com.github.novikovmn.spring2.service.CartService;
import com.github.novikovmn.spring2.service.OrderService;
import com.github.novikovmn.spring2.service.UserService;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

import java.math.BigDecimal;

@Route("cart")
public class CartView extends AbstractView {
    private final CartService cartService;
    private final OrderService orderService;
    private final UserService userService;

    public CartView(CartService cartService, OrderService orderService, UserService userService) {
        this.cartService = cartService;
        this.orderService = orderService;
        this.userService = userService;
        showHeader(Page.CART);
        initCartPage();
    }

    private void initCartPage() {
        if (cartService.getOrderItems().isEmpty()) {
            add(new Label("Корзина пуста"));
            return;
        }

        LabelWithValue<BigDecimal> orderPriceLabel =
                new LabelWithValue("Общая стоимость заказа: ", " р.", cartService.getPrice());
        add(orderPriceLabel);

        Grid<OrderItem> grid = new Grid<>(OrderItem.class);
        grid.setItems(cartService.getOrderItems());
        grid.setWidth("60%");
        grid.setColumns("product", "price");
        grid.addColumn(new ComponentRenderer<>(item -> {
            IntegerField quantityField = new IntegerField();
            quantityField.setValue(item.getQuantity());
            quantityField.setHasControls(true);
            quantityField.setMin(0);
            quantityField.addValueChangeListener(
                            event -> {
                                item.setQuantity(quantityField.getValue());
                                cartService.recalculate();
                                orderPriceLabel.setValue(cartService.getPrice());
                                grid.setItems(cartService.getOrderItems());
                            });
            return new HorizontalLayout(quantityField);
        }));
        setHorizontalComponentAlignment(Alignment.CENTER, grid);

        TextField addressField = new TextFieldWithPlaceholder("Введите адрес доставки");

        Button toOrderButton = new Button("Создать заказ", e -> {
            if (isNotAuthenticated()) {
                Notification.show("Для оформления заказа войдите на сайт");
                return;
            }
            if (user.getBalance().compareTo(cartService.getPrice()) < 0) {
                Notification.show("Недостаточно средств для оформления заказа");
                return;
            }
            String address = addressField.getValue();
            if (address.equals("")) {
                Notification.show("Заполните адрес доставки");
                return;
            }

            cartService.setAddress(address);
            orderService.saveOrder(user);
            UI.getCurrent().navigate(Page.ORDERS.getLocation());
            Notification.show("Заказ успешно сохранён и передан менеджеру");
        });

        add(grid, addressField, toOrderButton);
    }
}
