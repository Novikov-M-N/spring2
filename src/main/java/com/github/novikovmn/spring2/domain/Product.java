package com.github.novikovmn.spring2.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "products_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

//    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
//    private List<Price> priceHistory;

    @Override
    public String toString() { return this.title; } // Надо ли оно в таком виде?

    @Override
    public boolean equals(Object object) {
        if (object == this) { return true; }
        if (object == null || object.getClass() != this.getClass()) { return false; }
        Product product = (Product) object;
        return product.id == this.id
                && product.title.equals(this.title)
                && product.price.equals(this.price)
                && categoriesIsEquals(product.categories)
                ;
    }

    private boolean categoriesIsEquals(List<Category> categories) {
        if (categories == null && this.categories == null) { return true; }
        if (categories == this.categories) { return true; }
        if (categories.size() != this.categories.size()) { return false; }
        for (int i = 0; i < this.categories.size(); i++) {
            if (!this.categories.get(i).equals(categories.get(i))) { return false; }
        }
        return true;
    }
}
