package com.example.junit_test.modules.category.repositories;

import com.example.junit_test.modules.category.entities.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
  Page<Category> findAllByIsDeletedFalse(Pageable pageable);

  Category findByIdAndIsDeletedFalse(Integer id);

  Category findByNameAndIsDeletedFalse(String name);

  @Transactional
  @Modifying
  @Query("UPDATE Category e SET e.isDeleted = true WHERE e.id IN :ids")
  void deleteAllById(List<Integer> ids);

  Integer countAllByIdIn(List<Integer> ids);
}
