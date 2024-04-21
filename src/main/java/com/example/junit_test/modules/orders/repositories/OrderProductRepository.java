package com.example.junit_test.modules.orders.repositories;

import com.example.junit_test.modules.orders.entities.ImportOrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<ImportOrderProduct, Integer> {
    @Modifying
    @Query(value = "DELETE FROM order_product WHERE order_id = :orderId", nativeQuery = true)
    void deleteByOrder_Id(@Param("orderId") Integer orderId);

    List<ImportOrderProduct> findAllByImportOrderId(Integer id);

}
