package com.example.junit_test.controllers;

import com.example.junit_test.entities.SupplierEntity;
import com.example.junit_test.services.SupplierService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
@AllArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;
    @GetMapping()
    public List<SupplierEntity> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @GetMapping("{id}")
    public SupplierEntity getSupplierById(@PathVariable Integer id) {
        return supplierService.getSupplierById(id);
    }

    @PostMapping()
    public SupplierEntity createSupplier(@RequestBody SupplierEntity supplier) {
        return supplierService.createSupplier(supplier);
    }

    @PutMapping("/{id}")
    public SupplierEntity updateSupplier(@PathVariable Integer id, @RequestBody SupplierEntity supplier) {
        return supplierService.updateSupplier(id, supplier);
    }

    @DeleteMapping("/{id}")
    public void deleteSupplier(@PathVariable Integer id) {
        supplierService.deleteSupplier(id);
    }
}
