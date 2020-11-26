package com.github.novikovmn.spring2.repository;

import com.github.novikovmn.spring2.domain.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
