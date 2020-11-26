package com.github.novikovmn.spring2.repository;

import com.github.novikovmn.spring2.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
