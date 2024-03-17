package com.example.junit_test.modules.products.controllers;

import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.category.entities.CategoryEntity;
import com.example.junit_test.modules.products.dto.ProductDto;
import com.example.junit_test.modules.products.entities.ProductEntity;
import com.example.junit_test.modules.products.services.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<SystemResponse<List<ProductEntity>>> list() {
        return productService.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemResponse<ProductEntity>> getById(@PathVariable Integer id) {
        return productService.getById(id);
    }

    @PostMapping()
    public ResponseEntity<SystemResponse<Boolean>> create(@Valid @RequestBody ProductDto product) {
        return productService.create(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SystemResponse<Boolean>> update(@PathVariable Integer id, @Valid @RequestBody ProductDto product) {
        return productService.update(id, product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SystemResponse<Boolean>> delete(@PathVariable Integer id) {
        return productService.delete(id);
    }
}
