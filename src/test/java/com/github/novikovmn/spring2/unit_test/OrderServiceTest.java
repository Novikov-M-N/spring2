package com.github.novikovmn.spring2.unit_test;

import com.github.novikovmn.spring2.domain.*;
import com.github.novikovmn.spring2.repository.OrderRepository;
import com.github.novikovmn.spring2.service.CartService;
import com.github.novikovmn.spring2.service.OrderService;
import com.github.novikovmn.spring2.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class OrderServiceTest {
    @MockBean
    private OrderRepository testOrderRepository;
    @MockBean
    private CartService testCartService;
    @MockBean
    private UserService testUserService;

    @Autowired
    private OrderService testOrderService;

    private List<User> users;
    private List<Category> categories;
    private List<Product> products;
    private List<Order> orders;

    @BeforeEach
    private void initData() {
        categories = new ArrayList<>();
        products = new ArrayList<>();
        users = new ArrayList<>();
        orders = new ArrayList<>();
        /**
         * Инициализация списка категорий и продуктов: каждый последующий продукт имеет на одну категорию больше
         */
        for (int i = 1; i < 11; i++) {
            Category category = new Category();
            category.setId((long) i);
            category.setTitle("test category #" + i);
            categories.add(category);

            Product product = new Product();
            product.setId((long) i);
            product.setTitle("test product #" + i);
            product.setPrice(BigDecimal.valueOf((i) * 10.5));
            product.setCategories(new ArrayList<>());
            for (Category category1 : categories) { product.getCategories().add(category1); }
            products.add(product);
        }
        /**
         * Инициализация списка пользователей и их заказов - по одному заказу на пользователя.
         * Можно сделать по нескольку заказов на пользователя. Все заказы одинаковые.
         */
        for (int i = 1; i < 11; i++) {
            User user = new User();
            user.setId((long) i);
            user.setPhone(String.format("user %d phone", i));
            user.setFirstName(String.format("user %d first name", i));
            user.setLastName(String.format("user %d last name", i));
            user.setPassword(String.format("user %d password", i));
            user.setEmail(String.format("user %d email", i));
            user.setAge(20);
            Role roleCustomer = new Role();
            roleCustomer.setId(1L);
            roleCustomer.setName("ROLE_CUSTOMER");
            user.setRoles(new ArrayList<>(Arrays.asList(new Role[]{roleCustomer})));
            users.add(user);

            List<OrderItem> orderItems = new ArrayList<>();
            int itemCount = 1;
            BigDecimal orderPrice = new BigDecimal(0);
            for (Product product : products) {
                orderItems.add(new OrderItem(product, itemCount));
                BigDecimal itemPrice =
                        new BigDecimal(String.valueOf(product.getPrice().multiply(BigDecimal.valueOf(itemCount))));
                orderPrice = new BigDecimal(String.valueOf(orderPrice.add(itemPrice)));
                itemCount++;
            }
            Order order = new Order();
            order.setUser(user);
            order.setOrderItems(orderItems);
            order.setAddress(user.getPhone() + " address");
            order.setPhone(user.getPhone());
            order.setPrice(orderPrice);
            order.setStatus(Order.Status.MANAGING);
            orders.add(order);

            /**
             * Поведение UserService при поиске пользователя по id
             */
            Mockito.doReturn(user)
                    .when(testUserService)
                    .getById(user.getId());
        }

    }

    @Test
    public void saveOrderTest() {
        for (Order order : orders) {
            User user = order.getUser();
            List<OrderItem> orderItems = order.getOrderItems();

            Mockito.when(testCartService.getOrderItems()).thenReturn(orderItems);
            Mockito.when(testCartService.getAddress()).thenReturn(user.getPhone() + " address");
            Mockito.when(testCartService.getPrice()).thenReturn(order.getPrice());
            Mockito.when(testOrderRepository.save(order)).thenReturn(order);
            Mockito.doNothing().when(testCartService).clear();
            testOrderService.saveOrder(user);

            Mockito.verify(testUserService,
                    Mockito.times(1)).getById(ArgumentMatchers.eq(user.getId()));
            Mockito.verify(testCartService,
                    Mockito.times(1)).getOrderItems();
            Mockito.verify(testCartService,
                    Mockito.times(1)).getAddress();
            Mockito.verify(testCartService,
                    Mockito.times(1)).getPrice();
            Mockito.verify(testOrderRepository,
                    Mockito.times(1)).save(ArgumentMatchers.eq(order));
            Mockito.verify(testCartService,
                    Mockito.times(1)).clear();
            Mockito.clearInvocations(testUserService);
            Mockito.clearInvocations(testCartService);
            Mockito.clearInvocations(testOrderRepository);
        }
    }

    @Test
    public void getByUserIdTest() {
        for (User user : users) {
            List<Order> userOrders = new ArrayList<>();
            for (Order order : orders) {
                if (order.getUser().equals(user)) {userOrders.add(order); }
            }
            Mockito.when(testOrderRepository.findAllByUserId(user.getId())).thenReturn(userOrders);
            List<Order> getttedUserOrders = testOrderService.getByUser(user);

            Assertions.assertEquals(userOrders, getttedUserOrders);
            Mockito.verify(testOrderRepository,
                    Mockito.times(1)).findAllByUserId(user.getId());
        }
    }
}
