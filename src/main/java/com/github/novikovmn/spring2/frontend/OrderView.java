package com.github.novikovmn.spring2.frontend;

import com.github.novikovmn.spring2.domain.Order;
import com.github.novikovmn.spring2.domain.OrderItem;
import com.github.novikovmn.spring2.frontend.service.Page;
import com.github.novikovmn.spring2.service.OrderService;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Route("orders")
@Transactional
public class OrderView extends AbstractView {
    private final OrderService orderService;

    public OrderView(OrderService orderService) {
        this.orderService = orderService;
        showHeader(Page.ORDERS);
        initOrderView();
    }

    private void initOrderView() {
        if (orderService.getByUser(user).isEmpty()) {
            add(new Label("У вас нет активных заказов"));
            return;
        }

        Grid<Order> orderGrid = new Grid<>(Order.class);
        List<Order> orders = orderService.getByUser(user);
        orderGrid.setItems(orders);
        orderGrid.setColumns("address", "price", "status");
        orderGrid.addColumn(new ComponentRenderer<>(this::orderItemRows));
        add(orderGrid);
    }

    private VerticalLayout orderItemRows(Order order) {
        VerticalLayout layout = new VerticalLayout();

        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem: orderItems) {
            HorizontalLayout row = new HorizontalLayout();
            Label titleLabel = new Label(orderItem.getProduct().getTitle());
            titleLabel.setWidth(10, Unit.EM);
            row.add(titleLabel);
            String price = orderItem.getQuantity() + " x "
                    + orderItem.getProduct().getPrice() + " = "
                    + orderItem.getPrice();
            Label priceLabel = new Label(price);
            priceLabel.setWidth(25, Unit.EM);
            row.add(priceLabel);
            layout.add(row);
        }

        return layout;
    }

//    @Override
//    @Transactional
//    public void add(Component... components) {
//        super.add(components);
//    }
}
