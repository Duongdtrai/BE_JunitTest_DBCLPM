package com.example.junit_test.entities;

import com.example.junit_test.base.entities.BaseEntity;
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
