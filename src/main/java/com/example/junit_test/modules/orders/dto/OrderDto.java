package com.example.junit_test.modules.orders.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private String code;
    private String note;
    private Boolean status = false;
    private SupplierOrderDto supplier;
    private List<ProductOrderDto> products;
}


