package com.github.novikovmn.spring2.domain.dto;

import com.github.novikovmn.spring2.domain.Category;
import lombok.Data;

@Data
public class CategoryDto {
    private Long id;
    private String title;

    public CategoryDto(Category template) {
        this.id = template.getId();
        this.title = template.getTitle();
    }
}