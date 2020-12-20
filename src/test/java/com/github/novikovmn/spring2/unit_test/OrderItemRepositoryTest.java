package com.github.novikovmn.spring2.unit_test;

import com.github.novikovmn.spring2.Utils;
import com.github.novikovmn.spring2.domain.Category;
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
        for (OrderItem orderItem: orderItems) {
            testOrderItemRepository.save(orderItem);
            Assertions.assertEquals(orderItem, testOrderItemRepository.findById(orderItem.getId()).get());
        }
        int afterSize = Utils.sizeOf(testOrderItemRepository.findAll());
        Assertions.assertEquals(products.size(), afterSize - beforeSize);

        /**
         * Update test
         */
//        beforeSize = Utils.sizeOf(testCategoryRepository.findAll());
//        for (int i = 1; i < 11; i++) {
//            Category category = testCategoryRepository.findByTitle("test category #" + i).get();
//            category.setTitle(category.getTitle() + " edited");
//            testCategoryRepository.save(category);
//            Assertions.assertEquals(category, testCategoryRepository.findByTitle(category.getTitle()).get());
//        }
//        afterSize = Utils.sizeOf(testCategoryRepository.findAll());
//        Assertions.assertEquals(0, afterSize - beforeSize);
//
//        /**
//         * Delete test
//         */
//        beforeSize = Utils.sizeOf(testCategoryRepository.findAll());
//        for (int i = 1; i < 11; i++) {
//            Category category = testCategoryRepository.findByTitle("test category #" + i + " edited").get();
//            testCategoryRepository.delete(category);
//            Assertions.assertEquals(Optional.empty(), testCategoryRepository.findById(category.getId()));
//        }
//        afterSize = Utils.sizeOf(testCategoryRepository.findAll());
//        Assertions.assertEquals(10, beforeSize - afterSize);
    }
}
