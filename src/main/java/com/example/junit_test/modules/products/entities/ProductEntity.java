package com.example.junit_test.modules.products.entities;


import com.example.junit_test.base.entities.BaseEntity;
import com.example.junit_test.modules.category.entities.CategoryEntity;
import com.example.junit_test.modules.orders.entities.OrderEntity;
import com.example.junit_test.modules.orders.entities.OrderProductEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "products")
public class ProductEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String image;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @NotNull(message = "Price is required")
    private double price;

    private String description;

    @ManyToOne
    private CategoryEntity category;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<OrderProductEntity> orderProducts;

    private Boolean isDeleted;
}
