package com.example.junit_test.modules.category.controllers;

import com.example.junit_test.base.middleware.responses.ResponsePage;
import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.category.dto.CategoryDto;
import com.example.junit_test.modules.category.entities.CategoryEntity;
import com.example.junit_test.modules.category.services.CategoryService;
import com.example.junit_test.modules.orders.entities.OrderEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@AllArgsConstructor
@Tag(name = "Category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<SystemResponse<ResponsePage<CategoryEntity>>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return categoryService.list(page, size);
    }

    @GetMapping("{id}")
    public ResponseEntity<SystemResponse<CategoryEntity>>  getSupplierById(@PathVariable Integer id) {
        return categoryService.getById(id);
    }

    @PostMapping()
    public ResponseEntity<SystemResponse<CategoryEntity>> createSupplier(@Valid @RequestBody CategoryDto category) {
        return categoryService.create(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SystemResponse<Boolean>>  updateSupplier(@PathVariable Integer id, @Valid @RequestBody CategoryDto supplier) {
        return categoryService.update(id, supplier);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SystemResponse<Boolean>> deleteSupplier(@PathVariable Integer id) {
        return categoryService.delete(id);
    }
}
