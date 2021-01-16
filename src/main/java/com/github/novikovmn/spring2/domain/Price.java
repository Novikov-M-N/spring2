package com.github.novikovmn.spring2.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "prices")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "from_date")
    private Date from;

    @Column(name = "to_date")
    private Date to;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    @Override
    public String toString() {
        String priceString = price.toString();
        String fromString = from.toString().length() > 10 ? from.toString().substring(0, 10) : from.toString();
        String toString = to.toString().length() > 10 ? to.toString().substring(0, 10) : to.toString();
        return priceString + " " + fromString + " " + toString;
    }
}
