package com.github.novikovmn.spring2.service;

import com.github.novikovmn.spring2.domain.Order;
import com.github.novikovmn.spring2.domain.OrderItem;
import com.github.novikovmn.spring2.repository.OrderItemRepository;
import com.github.novikovmn.spring2.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final UserService userService;
    private final OrderItemRepository orderItemRepository;


    public OrderService(OrderRepository orderRepository,
                        CartService cartService,
                        UserService userService,
                        OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.userService = userService;
        this.orderItemRepository = orderItemRepository;
    }

    public void saveOrder() {
        Order order = new Order();
        order.setOrderItems(cartService.getOrderItems());
        order.setAddress(cartService.getAddress());
        order.setPhone(cartService.getPhone());
        order.setUser(userService.getById(4L));
        order.setPrice(cartService.getPrice());

        List<OrderItem> orderItems = order.getOrderItems().stream()
                .peek(orderItem -> orderItem.setOrder(order))
                .collect(Collectors.toList());

        orderRepository.save(order);

        orderItemRepository.saveAll(orderItems);
    }

    public List<Order> getByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }
}
