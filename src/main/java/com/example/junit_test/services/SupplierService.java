package com.example.junit_test.services;

import com.example.junit_test.entities.SupplierEntity;
import com.example.junit_test.repositories.ProductRepository;
import com.example.junit_test.repositories.SupplierRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public List<SupplierEntity> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public SupplierEntity getSupplierById(Integer id) {
        return supplierRepository.findSupplierById(id);
    }

    public SupplierEntity createSupplier(SupplierEntity supplier) {
        return supplierRepository.save(supplier);
    }

    public SupplierEntity updateSupplier(Integer id, SupplierEntity supplier) {
        SupplierEntity existingSupplier = supplierRepository.findSupplierById(id);
        existingSupplier.setName(supplier.getName());
        existingSupplier.setAddress(supplier.getAddress());
        existingSupplier.setContactNumber(supplier.getContactNumber());
        return supplierRepository.save(existingSupplier);
    }

    public void deleteSupplier(Integer id) {
        SupplierEntity supplier = supplierRepository.findSupplierById(id);
        supplierRepository.delete(supplier);
    }
}
