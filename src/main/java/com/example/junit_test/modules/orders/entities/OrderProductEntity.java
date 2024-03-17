package com.example.junit_test.modules.orders.entities;

import com.example.junit_test.modules.products.entities.ProductEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_product")
@IdClass(OrderProductEntity.class)
public class OrderProductEntity{
    @Id
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @ManyToOne
    private OrderEntity order;

    @Id
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne
    private ProductEntity product;

    private Integer quantity;
    private Double tax;
}
