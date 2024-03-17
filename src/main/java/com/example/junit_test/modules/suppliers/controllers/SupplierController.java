package com.example.junit_test.modules.suppliers.controllers;

import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.category.entities.CategoryEntity;
import com.example.junit_test.modules.suppliers.dto.SupplierDto;
import com.example.junit_test.modules.suppliers.entities.SupplierEntity;
import com.example.junit_test.modules.suppliers.services.SupplierService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/suppliers")
@AllArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;

    @GetMapping()
    public ResponseEntity<SystemResponse<List<SupplierEntity>>> getAllSuppliers() {
        return supplierService.list();
    }

    @GetMapping("{id}")
    public ResponseEntity<SystemResponse<SupplierEntity>> getSupplierById(@PathVariable Integer id) {
        return supplierService.getById(id);
    }

    @PostMapping()
    public ResponseEntity<SystemResponse<Boolean>> createSupplier(@Valid @RequestBody SupplierDto supplier) {
        return supplierService.create(supplier);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SystemResponse<Boolean>> updateSupplier(@PathVariable Integer id, @Valid @RequestBody SupplierDto supplier) {
        return supplierService.update(id, supplier);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SystemResponse<Boolean>> deleteSupplier(@PathVariable Integer id) {
        return supplierService.delete(id);
    }
}
