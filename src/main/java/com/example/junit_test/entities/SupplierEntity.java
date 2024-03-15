package com.example.junit_test.entities;


import com.example.junit_test.base.entities.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "suppliers")
public class SupplierEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String address;
    private String contactNumber;

    @OneToMany(mappedBy = "supplier")
    private List<ProductEntity> products;

    @OneToMany(mappedBy = "supplier")
    private List<OrderEntity> orders;
}
