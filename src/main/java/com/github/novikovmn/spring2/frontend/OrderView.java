package com.github.novikovmn.spring2.frontend;

import com.github.novikovmn.spring2.domain.Order;
import com.github.novikovmn.spring2.service.OrderService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;

@Route("orders")
public class OrderView extends AbstractView {
    private final OrderService orderService;

    public OrderView(OrderService orderService) {
        this.orderService = orderService;
        initOrderView();
    }

    private void initOrderView() {
        Grid<Order> orderGrid = new Grid<>(Order.class);
        orderGrid.setItems(orderService.getByUserId(4L));
        add(orderGrid);
    }
}
