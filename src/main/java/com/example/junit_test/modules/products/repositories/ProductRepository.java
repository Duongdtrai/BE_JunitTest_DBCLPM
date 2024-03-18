package com.example.junit_test.modules.products.repositories;

import com.example.junit_test.modules.products.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    ProductEntity findProductEntitiesByIdAndIsDeletedFalse(Integer id);
}