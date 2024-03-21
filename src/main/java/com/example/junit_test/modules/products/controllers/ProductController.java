package com.example.junit_test.modules.products.controllers;

import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.category.entities.CategoryEntity;
import com.example.junit_test.modules.products.dto.ProductDto;
import com.example.junit_test.modules.products.entities.ProductEntity;
import com.example.junit_test.modules.products.services.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
//@SecurityRequirement(name = "Authorization")
//@EnableMethodSecurity
public class ProductController {
    private final ProductService productService;

    @GetMapping()
    @PreAuthorize("hasAnyRole('MANAGER', 'STAFF')")
    public ResponseEntity<SystemResponse<List<ProductEntity>>> list() {
        return productService.list();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'STAFF')")
    public ResponseEntity<SystemResponse<ProductEntity>> getById(@PathVariable Integer id) {
        return productService.getById(id);
    }

    @PostMapping()
    @PreAuthorize("hasRole('MANAGER')")
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
