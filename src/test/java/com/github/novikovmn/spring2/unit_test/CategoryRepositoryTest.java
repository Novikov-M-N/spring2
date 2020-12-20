package com.github.novikovmn.spring2.unit_test;

import com.github.novikovmn.spring2.Utils;
import com.github.novikovmn.spring2.domain.Category;
import com.github.novikovmn.spring2.exception.CategoryNotFoundException;
import com.github.novikovmn.spring2.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository testCategoryRepository;

    @Test
    public void crudTest() {
        int beforeSize = Utils.sizeOf(testCategoryRepository.findAll());
        for (int i = 1; i < 11; i++) {
            Category category = new Category();
            category.setTitle("test category #" + i);
            testCategoryRepository.save(category);
            Assertions.assertEquals(category, testCategoryRepository.findByTitle(category.getTitle()).get());
        }
        int afterSize = Utils.sizeOf(testCategoryRepository.findAll());
        Assertions.assertEquals(10, afterSize - beforeSize);

        beforeSize = Utils.sizeOf(testCategoryRepository.findAll());
        for (int i = 1; i < 11; i++) {
            Category category = testCategoryRepository.findByTitle("test category #" + i).get();
            category.setTitle(category.getTitle() + " edited");
            testCategoryRepository.save(category);
            Assertions.assertEquals(category, testCategoryRepository.findByTitle(category.getTitle()).get());
        }
        afterSize = Utils.sizeOf(testCategoryRepository.findAll());
        Assertions.assertEquals(0, afterSize - beforeSize);

        beforeSize = Utils.sizeOf(testCategoryRepository.findAll());
        for (int i = 1; i < 11; i++) {
            Category category = testCategoryRepository.findByTitle("test category #" + i + " edited").get();
            testCategoryRepository.delete(category);
            Assertions.assertEquals(Optional.empty(), testCategoryRepository.findById(category.getId()));
        }
        afterSize = Utils.sizeOf(testCategoryRepository.findAll());
        Assertions.assertEquals(10, beforeSize - afterSize);
    }

}
