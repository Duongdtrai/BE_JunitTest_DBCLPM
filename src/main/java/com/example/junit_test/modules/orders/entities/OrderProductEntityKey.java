package com.example.junit_test.modules.orders.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductEntityKey implements Serializable {
    private Integer orderId;
    private Integer productId;
}
