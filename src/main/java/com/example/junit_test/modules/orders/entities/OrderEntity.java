package com.example.junit_test.modules.orders.entities;


import com.example.junit_test.base.entities.BaseEntity;
import com.example.junit_test.modules.products.entities.ProductEntity;
import com.example.junit_test.modules.suppliers.entities.SupplierEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class OrderEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double tax;

    private String code;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private SupplierEntity supplier;

    @OneToMany(mappedBy = "order") // Thêm FetchType.EAGER ở đây
    private List<OrderProductEntity> orderProducts;

    private Boolean status;
}
