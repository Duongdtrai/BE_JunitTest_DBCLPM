package com.example.junit_test.modules.orders.dto;

import com.example.junit_test.modules.orders.entities.OrderEntity;
import com.example.junit_test.modules.suppliers.entities.SupplierEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class SupplierDto extends SupplierEntity {
    @NotNull(message = "Id Supplier is required")
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "Id must be an integer")
    private Integer id;


    @JsonIgnore
    private String name;

    @JsonIgnore
    private String address;

    @JsonIgnore
    private String phoneNumber;

    @JsonIgnore
    private List<OrderEntity> orders;
}
