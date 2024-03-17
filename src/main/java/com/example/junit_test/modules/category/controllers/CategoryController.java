package com.example.junit_test.modules.category.controllers;

import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.category.dto.CategoryDto;
import com.example.junit_test.modules.category.entities.CategoryEntity;
import com.example.junit_test.modules.category.services.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<SystemResponse<List<CategoryEntity>>> list() {
        return categoryService.list();
    }

    @GetMapping("{id}")
    public ResponseEntity<SystemResponse<CategoryEntity>>  getSupplierById(@PathVariable Integer id) {
        return categoryService.getById(id);
    }

    @PostMapping()
    public ResponseEntity<SystemResponse<Boolean>> createSupplier(@Valid @RequestBody CategoryDto category) {
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
