package com.example.junit_test.modules.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
 public class ProductOrderDto {
    private Integer id;
    private Integer quantity;
    private Double tax;
}