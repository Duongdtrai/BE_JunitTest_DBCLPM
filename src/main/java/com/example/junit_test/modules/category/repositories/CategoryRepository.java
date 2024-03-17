package com.example.junit_test.modules.category.repositories;

import com.example.junit_test.modules.category.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    CategoryEntity findByIdAndIsDeletedFalse(Integer id);
    CategoryEntity findByNameAndIsDeletedFalse(String name);
}
