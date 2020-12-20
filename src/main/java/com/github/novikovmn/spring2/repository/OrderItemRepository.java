package com.github.novikovmn.spring2.repository;

import com.github.novikovmn.spring2.domain.OrderItem;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {
    Optional<OrderItem> findByPrice(BigDecimal price);
}
