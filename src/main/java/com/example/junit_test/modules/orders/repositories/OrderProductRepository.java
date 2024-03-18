package com.example.junit_test.modules.orders.repositories;

import com.example.junit_test.modules.orders.entities.OrderEntity;
import com.example.junit_test.modules.orders.entities.OrderProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProductEntity, Integer> {
    void deleteByOrder_Id(Integer orderId);
}
