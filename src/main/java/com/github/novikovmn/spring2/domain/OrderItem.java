package com.github.novikovmn.spring2.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private Integer quantity;

//    @ManyToOne
//    @JoinColumn(name = "order_id")
//    private Order order;

    public OrderItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
        this.price = new BigDecimal(String.valueOf(new BigDecimal(quantity).multiply(product.getPrice())));
    }

    public OrderItem(Product product) {
        this(product, 1);
    }

    public OrderItem() {}

    public void increment() {   // Сделать с параметром количество, на которое увеличивать
        this.quantity++;
        this.price = new BigDecimal(String.valueOf(this.price.add(this.product.getPrice())));
    }

    public void decrement() {   // Сделать с параметром количество, на которое уменьшать
        if (this.quantity == 0) { return; }
        this.quantity--;
        this.price = new BigDecimal(String.valueOf(this.price.subtract(this.product.getPrice())));
    }

    public boolean equals(Object object) {
        if (object == this) { return true; }
        if (object == null || object.getClass() != this.getClass()) { return false; }
        OrderItem orderItem = (OrderItem) object;
        return orderItem.id == this.id
//                && orderItem.product.equals(this.product)
                && orderItem.quantity.equals(this.quantity)
//                && orderItem.price.equals(this.price)
                ;
    }

}