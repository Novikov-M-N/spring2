package com.github.novikovmn.spring2.frontend;

import com.github.novikovmn.spring2.domain.OrderItem;
import com.github.novikovmn.spring2.service.CartService;
import com.github.novikovmn.spring2.service.OrderService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

@Route("cart")
public class CartView extends AbstractView {
    private final CartService cartService;
    private final OrderService orderService;

    public CartView(CartService cartService, OrderService orderService) {
        this.cartService = cartService;
        this.orderService = orderService;
        initCartPage();
    }

    private void initCartPage() {
        Grid<OrderItem> grid = new Grid<>(OrderItem.class);
        grid.setItems(cartService.getOrderItems());
        grid.setWidth("60%");
        grid.setColumns("product", "price", "quantity");
        grid.addColumn(new ComponentRenderer<>(item -> {
            Button plusButton = new Button("+", action -> {
                item.increment();
                grid.setItems(cartService.getOrderItems());
            });
            Button minusButton = new Button("-", action -> {
                item.decrement();
                grid.setItems(cartService.getOrderItems());
            });
            return new HorizontalLayout(plusButton,minusButton);
        }));

        TextField addressField = initTextFieldWithPlaceholder("Введите адрес доставки");

        Button toOrderButton = new Button("Создать заказ", e -> {
            cartService.setAddress(addressField.getValue());
            orderService.saveOrder(1L);
            Notification.show("Заказ успешно сохранён и передан менеджеру");
        });

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(grid, addressField, toOrderButton);
    }
}
