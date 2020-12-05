package com.github.novikovmn.spring2.domain.dto;

import com.github.novikovmn.spring2.domain.Category;
import com.github.novikovmn.spring2.domain.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductDto {
    private Long id;
    private String title;
    private BigDecimal price;
    private List<CategoryDto> categories;

    public ProductDto(Product template) {
        this.id = template.getId();
        this.title = template.getTitle();
        this.price = template.getPrice();
        this.categories = new ArrayList<>();
        for (Category category : template.getCategories()) {
            this.categories.add(new CategoryDto(category));
        }
    }
}