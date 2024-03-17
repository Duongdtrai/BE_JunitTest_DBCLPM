package com.example.junit_test.modules.orders.repositories;

import com.example.junit_test.modules.orders.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
}
