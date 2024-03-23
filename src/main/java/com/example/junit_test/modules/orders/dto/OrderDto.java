package com.example.junit_test.modules.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Boolean status = false;
    private SupplierOrderDto supplier;
    private List<ProductOrderDto> products;
}


