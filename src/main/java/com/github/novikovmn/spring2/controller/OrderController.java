package com.github.novikovmn.spring2.controller;

import com.github.novikovmn.spring2.domain.Order;
import com.github.novikovmn.spring2.domain.dto.OrderDto;
import com.github.novikovmn.spring2.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    public List<OrderDto> getAllOrders() {
        List<OrderDto> result = new ArrayList<>();
        List<Order> orders = orderRepository.findAll();
        for (Order order : orders) {
            result.add(new OrderDto(order));
        }
        return result;
    }
}
