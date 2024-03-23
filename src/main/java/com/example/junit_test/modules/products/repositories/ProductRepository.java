package com.example.junit_test.modules.products.repositories;

import com.example.junit_test.modules.products.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    Page<ProductEntity> findAllByIsDeletedFalse(Pageable pageable);
    ProductEntity findProductEntitiesByIdAndIsDeletedFalse(Integer id);
}