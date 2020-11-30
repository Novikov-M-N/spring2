package com.github.novikovmn.spring2.repository;

import com.github.novikovmn.spring2.domain.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findAll();
    List<Order> findAllByUserId(Long userId);
}
