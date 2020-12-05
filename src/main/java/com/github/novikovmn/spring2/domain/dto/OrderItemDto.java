package com.github.novikovmn.spring2.domain.dto;

import com.github.novikovmn.spring2.domain.OrderItem;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private Long id;
    private ProductDto product;
    private Integer quantity;
    private BigDecimal price;

    public OrderItemDto(OrderItem template){
        this.id = template.getId();
        this.product = new ProductDto(template.getProduct());
        this.quantity = template.getQuantity();
        this.price = template.getPrice();
    }
}
