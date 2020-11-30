package com.github.novikovmn.spring2.frontend;

import com.github.novikovmn.spring2.service.CartService;
import com.github.novikovmn.spring2.service.OrderService;
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

    }
}
