package com.example.junit_test.modules.category.controllers;

import com.example.junit_test.base.middleware.responses.ResponsePage;
import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.category.entities.Category;
import com.example.junit_test.modules.category.services.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@AllArgsConstructor
@Tag(name = "Category")
@Validated
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping()
  public ResponseEntity<SystemResponse<ResponsePage<Category>>> list(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") int size
  ) {
    return categoryService.list(page, size);
  }

  @GetMapping("{id}")
  public ResponseEntity<SystemResponse<Category>> getSupplierById(@PathVariable Integer id) {
    return categoryService.getById(id);
  }

  @PostMapping()
  public ResponseEntity<SystemResponse<Category>> createSupplier(@Valid @RequestBody Category category, Errors errors) {
    return categoryService.create(category);
  }

  @PutMapping("/{id}")
  public ResponseEntity<SystemResponse<Boolean>> updateSupplier(@PathVariable Integer id, @Valid @RequestBody Category supplier, Errors errors) {
    return categoryService.update(id, supplier);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<SystemResponse<Boolean>> deleteSupplier(@PathVariable Integer id) {
    return categoryService.delete(id);
  }


  @DeleteMapping("/all")
  public ResponseEntity<SystemResponse<Boolean>> deleteSupplierByIds(@RequestBody Integer[] arr) {
    return categoryService.deleteAll(arr);
  }
}
