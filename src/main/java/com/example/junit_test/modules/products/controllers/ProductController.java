package com.example.junit_test.modules.products.controllers;

import com.example.junit_test.base.middleware.responses.ResponsePage;
import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.products.entities.Product;
import com.example.junit_test.modules.products.services.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
//@SecurityRequirement(name = "Authorization")
//@EnableMethodSecurity
@Tag(name = "Product")
@Validated
public class ProductController {
    private final ProductService productService;

    @GetMapping()
    @PreAuthorize("hasAnyRole('MANAGER', 'STAFF')")
    public ResponseEntity<SystemResponse<ResponsePage<Product>>> list(@RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size) {
        return productService.list(page, size);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'STAFF')")
    public ResponseEntity<SystemResponse<Product>> getById(@PathVariable Integer id) {
        return productService.getById(id);
    }

    @PostMapping()
//    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<SystemResponse<Boolean>> create(@Valid @RequestBody Product product, Errors errors) {
        return productService.create(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SystemResponse<Boolean>> update(@PathVariable Integer id, @Valid @RequestBody Product product, Errors errors) {
        return productService.update(id, product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SystemResponse<Boolean>> delete(@PathVariable Integer id) {
        return productService.delete(id);
    }


    @DeleteMapping("/all")
    public ResponseEntity<SystemResponse<Boolean>> deleteProductsById(@RequestBody Integer[] arr) {
        return productService.deleteAll(arr);
    }
}
