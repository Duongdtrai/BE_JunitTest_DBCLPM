package com.example.junit_test.entities;


import com.example.junit_test.base.entities.BaseEntity;
import com.example.junit_test.enums.Tags;
import com.example.junit_test.enums.Type;
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
@Table(name = "products")
public class ProductEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String image;
    private String name;
    private Integer quantity;
    private double price;

    @ManyToMany(mappedBy = "products")
    private List<OrderEntity> orders;

    private String description;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Enumerated(EnumType.STRING)
    private Tags tags;
}
