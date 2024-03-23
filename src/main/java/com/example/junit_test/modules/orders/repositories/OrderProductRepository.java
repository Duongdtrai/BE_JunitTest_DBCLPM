package com.example.junit_test.modules.orders.repositories;

import com.example.junit_test.modules.orders.entities.OrderEntity;
import com.example.junit_test.modules.orders.entities.OrderProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderProductRepository extends JpaRepository<OrderProductEntity, Integer> {
    @Modifying
    @Query(value = "DELETE FROM order_product WHERE order_id = :orderId", nativeQuery = true)
    void deleteByOrder_Id(@Param("orderId") Integer orderId);

}
