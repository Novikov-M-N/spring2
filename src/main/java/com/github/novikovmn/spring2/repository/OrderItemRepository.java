package com.github.novikovmn.spring2.repository;

import com.github.novikovmn.spring2.domain.OrderItem;
import org.springframework.data.repository.CrudRepository;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {
}
