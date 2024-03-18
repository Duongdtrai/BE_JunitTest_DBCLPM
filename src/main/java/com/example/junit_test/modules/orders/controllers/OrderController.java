package com.example.junit_test.modules.orders.controllers;


import com.example.junit_test.base.middleware.responses.Response;
import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.orders.dto.OrderDto;
import com.example.junit_test.modules.orders.entities.OrderEntity;
import com.example.junit_test.modules.orders.repositories.OrderRepository;
import com.example.junit_test.modules.orders.services.OrderService;
import com.example.junit_test.modules.products.entities.ProductEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping()
    public ResponseEntity<SystemResponse<List<OrderEntity>>> list() {
        return orderService.list();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<SystemResponse<OrderEntity>> getById(@PathVariable Integer orderId) {
        return orderService.getById(orderId);
    }

    @PostMapping
    public ResponseEntity<SystemResponse<OrderEntity>> create(@Valid @RequestBody OrderDto order) {
        return orderService.create(order);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<SystemResponse<Boolean>> update(@PathVariable Integer orderId, @RequestBody OrderDto updatedOrder) {
        return orderService.update(orderId, updatedOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<SystemResponse<Boolean>> delete(@PathVariable Integer orderId) {
        return orderService.delete(orderId);
    }
}
