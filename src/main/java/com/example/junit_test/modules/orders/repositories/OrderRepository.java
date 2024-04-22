package com.example.junit_test.modules.orders.repositories;

import com.example.junit_test.modules.orders.entities.ImportOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<ImportOrder, Integer> {
    ImportOrder findOrderEntitiesById(Integer id);
    ImportOrder findImportOrderByIdAndStatusIsFalse(Integer id);
    List<ImportOrder> findImportOrderByCode(String code);
    Page<ImportOrder> findAll(Pageable pageable);
    Page<ImportOrder> findAllByStatus(Pageable pageable, Boolean status);
}
