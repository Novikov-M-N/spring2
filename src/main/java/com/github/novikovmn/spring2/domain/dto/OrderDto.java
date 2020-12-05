package com.github.novikovmn.spring2.domain.dto;

import com.github.novikovmn.spring2.domain.Order;
import com.github.novikovmn.spring2.domain.OrderItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private UserDto user;
    private List<OrderItemDto> orderItems;
    private String phone;
    private String address;
    private BigDecimal price;
    private Order.Status status;

    public OrderDto(Order template) {
        this.id = template.getId();
        this.user = new UserDto(template.getUser());
        this.orderItems = new ArrayList<>();
        for (OrderItem orderItem : template.getOrderItems()) {
            this.orderItems.add(new OrderItemDto(orderItem));
        }
        this.phone = template.getPhone();
        this.address = template.getAddress();
        this.price = template.getPrice();
        this.status = template.getStatus();
    }
}
