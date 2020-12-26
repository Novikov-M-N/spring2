package com.github.novikovmn.spring2.unit_test;

import com.github.novikovmn.spring2.Utils;
import com.github.novikovmn.spring2.domain.OrderItem;
import com.github.novikovmn.spring2.domain.Product;
import com.github.novikovmn.spring2.repository.OrderItemRepository;
import com.github.novikovmn.spring2.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class OrderItemRepositoryTest {
    @Autowired
    OrderItemRepository testOrderItemRepository;
    @Autowired
    ProductRepository testProductRepository;

    @Test
    public void CrudTest() {
        /**
         * Prepare test objects
         */
        List<Product> products = testProductRepository.findAll();
        List<OrderItem> orderItems = new ArrayList<>();
        int i = 1;
        for (Product product : products) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(i);
            orderItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(i)));
            orderItems.add(orderItem);
            i++;
        }

        /**
         * Create test
         */
        int beforeSize = Utils.sizeOf(testOrderItemRepository.findAll());
        for (OrderItem orderItem : orderItems) {
            testOrderItemRepository.save(orderItem);
            Assertions.assertEquals(orderItem, testOrderItemRepository.findById(orderItem.getId()).get());
        }
        int afterSize = Utils.sizeOf(testOrderItemRepository.findAll());
        Assertions.assertEquals(products.size(), afterSize - beforeSize);

        /**
         * Update test
         */
        beforeSize = Utils.sizeOf(testOrderItemRepository.findAll());
        for (OrderItem orderItem : orderItems) {
            BigDecimal beforePrice = orderItem.getPrice();
            int countOfIncrements = 2;
            for (int j = 0; j < countOfIncrements; j++) {
                orderItem.increment();
            }
            BigDecimal priceDifference = orderItem.getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(countOfIncrements));
            testOrderItemRepository.save(orderItem);
            Assertions.assertEquals(orderItem, testOrderItemRepository.findById(orderItem.getId()).get());
            Assertions.assertEquals(priceDifference, orderItem.getPrice().subtract(beforePrice));
        }
        afterSize = Utils.sizeOf(testOrderItemRepository.findAll());
        Assertions.assertEquals(0, afterSize - beforeSize);

        /**
         * Delete test
         */
        beforeSize = Utils.sizeOf(testOrderItemRepository.findAll());
        for (OrderItem orderItem : orderItems) {
            testOrderItemRepository.delete(orderItem);
            Assertions.assertEquals(Optional.empty(), testOrderItemRepository.findById(orderItem.getId()));
        }
        afterSize = Utils.sizeOf(testOrderItemRepository.findAll());
        Assertions.assertEquals(products.size(), beforeSize - afterSize);
    }
}
