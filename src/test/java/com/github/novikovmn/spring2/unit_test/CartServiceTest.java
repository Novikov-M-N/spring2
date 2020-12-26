package com.github.novikovmn.spring2.unit_test;

import com.github.novikovmn.spring2.domain.Product;
import com.github.novikovmn.spring2.service.CartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class CartServiceTest {
    @Autowired
    private CartService testCartService;

    @Test
    public void cartFillingTest() {
        for (int i = 0; i < 5; i++) {
            Product product = new Product();
            product.setId((long) i);
            product.setPrice(new BigDecimal(100 + (long) i * 10));
            product.setTitle("Product #" + (long) i);
            testCartService.add(product);
        }
        Assertions.assertEquals(5, testCartService.getOrderItems().size());
    }
}
