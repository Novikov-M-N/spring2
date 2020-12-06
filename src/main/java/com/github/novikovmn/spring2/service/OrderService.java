package com.github.novikovmn.spring2.service;

import com.github.novikovmn.spring2.domain.Order;
import com.github.novikovmn.spring2.domain.OrderItem;
import com.github.novikovmn.spring2.domain.User;
import com.github.novikovmn.spring2.repository.OrderItemRepository;
import com.github.novikovmn.spring2.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        User user = userService.getById(1L);
        Order order = new Order();
        order.setUser(user);
        order.setOrderItems(cartService.getOrderItems());
        order.setAddress(cartService.getAddress());
        order.setPhone(user.getPhone());
        order.setPrice(cartService.getPrice());
        order.setStatus(Order.Status.MANAGING);

        orderRepository.save(order);
        cartService.clear();
    }

    @Transactional
    public List<Order> getByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }
}
