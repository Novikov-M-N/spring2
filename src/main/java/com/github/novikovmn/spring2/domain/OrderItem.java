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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.price = this.product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    public void increment() {
        increment(1);
    }

    public void increment(int quantity) {
        this.quantity+=quantity;
        this.price = new BigDecimal(String.valueOf(
                this.price.add(this.product.getPrice().multiply(BigDecimal.valueOf(quantity)))));
    }

    public void decrement() {
        decrement(1);
    }

    public void decrement(int quantity) {
        if (this.quantity == 0) { return; }
        this.quantity-=quantity;
        if (this.quantity <= 0) {
            this.quantity = 0;
            this.price = BigDecimal.valueOf(0);
            return;
        }
        this.price = new BigDecimal(String.valueOf(
                this.price.subtract(this.product.getPrice()).multiply(BigDecimal.valueOf(quantity))));
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) { return true; }
        if (object == null || object.getClass() != this.getClass()) { return false; }
        OrderItem orderItem = (OrderItem) object;
        return orderItem.id == this.id
                && orderItem.product.equals(this.product)
                && orderItem.quantity == this.quantity
                && orderItem.price.equals(this.price)
                ;
    }

}