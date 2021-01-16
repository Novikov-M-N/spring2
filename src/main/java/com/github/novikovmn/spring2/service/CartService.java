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

    @PostConstruct
    public void clear() { this.orderItems = new ArrayList<>(); }

    public void add(Product product) {
        add(product, 1);
    }

    public void add(Product product, int quantity) {
        for (OrderItem orderItem: orderItems) {
            if (orderItem.getProduct().getId().equals(product.getId())) {
                orderItem.increment(quantity);
                recalculate();
                return;
            }
        }
        orderItems.add(new OrderItem(product, quantity));
        recalculate();
    }

    public void recalculate() {
        price = new BigDecimal(0);
        // Попробовать обойтись без этой функции
        orderItems.stream().forEach(orderItem -> price = price.add(orderItem.getPrice()));
    }
}
