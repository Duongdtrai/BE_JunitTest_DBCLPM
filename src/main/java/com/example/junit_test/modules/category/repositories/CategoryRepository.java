package com.example.junit_test.modules.category.repositories;

import com.example.junit_test.modules.category.entities.CategoryEntity;
import com.example.junit_test.modules.orders.entities.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    Page<CategoryEntity> findAllByIsDeletedFalse(Pageable pageable);

    CategoryEntity findByIdAndIsDeletedFalse(Integer id);
    CategoryEntity findByNameAndIsDeletedFalse(String name);
}
