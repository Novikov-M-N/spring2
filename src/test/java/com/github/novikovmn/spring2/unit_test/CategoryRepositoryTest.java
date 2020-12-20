package com.github.novikovmn.spring2.unit_test;

import com.github.novikovmn.spring2.Utils;
import com.github.novikovmn.spring2.domain.Category;
import com.github.novikovmn.spring2.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository testCategoryRepository;

    @Test
    public void crudTest() {
        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Category category = new Category();
            category.setTitle("test category #" + i);
            categories.add(category);
        }

        /**
         * Create test
         */
        int beforeSize = Utils.sizeOf(testCategoryRepository.findAll());
        for (Category category : categories) {
            testCategoryRepository.save(category);
            Assertions.assertEquals(category, testCategoryRepository.findById(category.getId()).get());
        }
        int afterSize = Utils.sizeOf(testCategoryRepository.findAll());
        Assertions.assertEquals(10, afterSize - beforeSize);

        /**
         * Update test
         */
        beforeSize = Utils.sizeOf(testCategoryRepository.findAll());
        for (Category category : categories) {
            category.setTitle(category.getTitle() + " edited");
            testCategoryRepository.save(category);
            Assertions.assertEquals(category, testCategoryRepository.findById(category.getId()).get());
        }
        afterSize = Utils.sizeOf(testCategoryRepository.findAll());
        Assertions.assertEquals(0, afterSize - beforeSize);

        /**
         * Delete test
         */
        beforeSize = Utils.sizeOf(testCategoryRepository.findAll());
        for (Category category : categories) {
            testCategoryRepository.delete(category);
            Assertions.assertEquals(Optional.empty(), testCategoryRepository.findById(category.getId()));
        }
        afterSize = Utils.sizeOf(testCategoryRepository.findAll());
        Assertions.assertEquals(10, beforeSize - afterSize);
    }

}
