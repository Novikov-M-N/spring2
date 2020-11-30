package com.github.novikovmn.spring2.service;

import com.github.novikovmn.spring2.domain.OrderItem;
import com.github.novikovmn.spring2.domain.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Setter
@Getter
public class CartService {
    private List<OrderItem> orderItems;
    private BigDecimal price;
    private String address; // Может, получать через связанный объект User
    private String phone; // Может, получать через связанный объект User

    @PostConstruct
    public void init() { orderItems = new ArrayList<>(); }

    public void add(Product product, Integer quantity) {
        for (OrderItem orderItem: orderItems) {
            if (orderItem.getProduct().getId().equals(product.getId())) {
                for (int i = 0; i < quantity; i++) {
                    orderItem.increment();
                }
                recalculate();
                return;
            }
        }
    }

    public void recalculate() { // Может, private?
        price = new BigDecimal(0);
        // Попробовать обойтись без этой функции
        orderItems.stream().forEach(orderItem -> price = price.add(orderItem.getPrice()));
    }
}
