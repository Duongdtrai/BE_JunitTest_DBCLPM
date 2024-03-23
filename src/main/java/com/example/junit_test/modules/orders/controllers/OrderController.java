package com.example.junit_test.modules.orders.controllers;


import com.example.junit_test.base.middleware.responses.Response;
import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.orders.ExcelHelper;
import com.example.junit_test.modules.orders.dto.OrderDto;
import com.example.junit_test.modules.orders.entities.OrderEntity;
import com.example.junit_test.modules.orders.repositories.OrderRepository;
import com.example.junit_test.modules.orders.services.OrderService;
import com.example.junit_test.modules.products.entities.ProductEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(path = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SystemResponse<String>> importFile(@RequestPart("file") MultipartFile file) {
        String message = "";
        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                orderService.importFile(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return Response.badRequest(HttpStatus.OK.hashCode(), message);
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return Response.badRequest(HttpStatus.EXPECTATION_FAILED.hashCode(), message);
            }
        }
        message = "Please upload an excel file!";
        return Response.badRequest(HttpStatus.BAD_REQUEST.hashCode(), message);
    }
}
