package com.example.junit_test.modules.suppliers.controllers;

import com.example.junit_test.base.middleware.responses.ResponsePage;
import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.suppliers.dto.SupplierDto;
import com.example.junit_test.modules.suppliers.entities.Supplier;
import com.example.junit_test.modules.suppliers.services.SupplierService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/suppliers")
@AllArgsConstructor
@Validated
@Tag(name = "Supplier")
public class SupplierController {
    private final SupplierService supplierService;

    @GetMapping()
    public ResponseEntity<SystemResponse<ResponsePage<Supplier>>> getAllSuppliers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return supplierService.list(page, size);
    }

    @GetMapping("{id}")
    public ResponseEntity<SystemResponse<Supplier>> getSupplierById(@PathVariable Integer id) {
        return supplierService.getById(id);
    }

    @PostMapping()
    public ResponseEntity<SystemResponse<Boolean>> createSupplier(@Valid @RequestBody Supplier supplier, Errors errors) {
        return supplierService.create(supplier);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SystemResponse<Boolean>> updateSupplier(@PathVariable Integer id, @Valid @RequestBody Supplier supplier, Errors errors) {
        return supplierService.update(id, supplier);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SystemResponse<Boolean>> deleteSupplier(@PathVariable Integer id) {
        return supplierService.delete(id);
    }


    @DeleteMapping("/all")
    public ResponseEntity<SystemResponse<Boolean>> deleteSupplierByIds(@RequestBody Integer[] arr) {
        return supplierService.deleteAll(arr);
    }
}
