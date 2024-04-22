package com.example.junit_test.modules.products.repositories;

import com.example.junit_test.modules.products.entities.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
  Page<Product> findAllByIsDeletedFalse(Pageable pageable);

  Product findProductEntitiesByIdAndIsDeletedFalse(Integer id);

  @Transactional
  @Modifying
  @Query("UPDATE Product e SET e.isDeleted = true WHERE e.id IN :ids")
  void deleteAllById(List<Integer> ids);

  Integer countAllByIdIn(List<Integer> ids);
}