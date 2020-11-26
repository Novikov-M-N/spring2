package com.github.novikovmn.spring2.repository;

import com.github.novikovmn.spring2.domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findAll();
}
