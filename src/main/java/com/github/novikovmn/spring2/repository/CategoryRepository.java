package com.github.novikovmn.spring2.repository;

import com.github.novikovmn.spring2.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findByTitle(String title);
    List<Category> findAll();
}
