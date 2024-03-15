package com.example.junit_test.controllers;


import com.example.junit_test.entities.OrderEntity;
import com.example.junit_test.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderRepository orderRepository;

    @PostMapping
    public ResponseEntity<OrderEntity> createOrder(@RequestBody OrderEntity order) {
        OrderEntity createdOrder = orderRepository.save(order);
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderEntity> getOrder(@PathVariable Integer orderId) {
        Optional<OrderEntity> order = orderRepository.findById(orderId);
        return order.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderEntity> updateOrder(@PathVariable Integer orderId, @RequestBody OrderEntity updatedOrder) {
        Optional<OrderEntity> existingOrder = orderRepository.findById(orderId);
        if (existingOrder.isPresent()) {
            OrderEntity order = existingOrder.get();
            OrderEntity updatedOrderEntity = orderRepository.save(order);
            return ResponseEntity.ok(updatedOrderEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer orderId) {
        Optional<OrderEntity> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            orderRepository.delete(order.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
