package com.example.junit_test.modules.suppliers.services;

import com.example.junit_test.base.middleware.responses.Response;
import com.example.junit_test.base.middleware.responses.SystemResponse;
import com.example.junit_test.modules.category.entities.CategoryEntity;
import com.example.junit_test.modules.suppliers.dto.SupplierDto;
import com.example.junit_test.modules.suppliers.entities.SupplierEntity;
import com.example.junit_test.modules.suppliers.repositories.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public ResponseEntity<SystemResponse<List<SupplierEntity>>> list() {
        try {
            List<SupplierEntity> data = supplierRepository.findAll(Sort.by(Sort.Direction.DESC, "updatedAt"));
            return Response.ok(data);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }

    public ResponseEntity<SystemResponse<SupplierEntity>> getById(Integer id) {
        try {
            SupplierEntity data = supplierRepository.findSupplierByIdAndIsDeletedFalse(id);
            if (data == null) {
                return Response.badRequest(404, "Category is not exist");
            }
            return Response.ok(data);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }

    public ResponseEntity<SystemResponse<Boolean>> create(SupplierDto supplier) {
        try {
            SupplierEntity supplierExist = supplierRepository.findByNameAndAddressAndPhoneNumber(supplier.getName(), supplier.getAddress(), supplier.getPhoneNumber());
            if (supplierExist == null) {
                SupplierEntity newSupplier = new SupplierEntity();
                newSupplier.setName(supplier.getName());
                newSupplier.setAddress(supplier.getAddress());
                newSupplier.setPhoneNumber(supplier.getPhoneNumber());
                newSupplier.setIsDeleted(false);
                supplierRepository.save(newSupplier);
                return Response.ok(true);
            }
            return Response.badRequest(404, "Category is exist name");

        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }

    public ResponseEntity<SystemResponse<Boolean>> update(Integer id, SupplierDto supplier) {
        try {
            SupplierEntity existingSupplier = supplierRepository.findSupplierByIdAndIsDeletedFalse(id);
            if (supplier == null) {
                return Response.badRequest(404, "Supplier is not exist");
            }
            existingSupplier.setName(supplier.getName());
            existingSupplier.setAddress(supplier.getAddress());
            existingSupplier.setPhoneNumber(supplier.getPhoneNumber());
            supplierRepository.save(existingSupplier);
            return Response.ok(true);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }


    }

    public ResponseEntity<SystemResponse<Boolean>> delete(Integer id) {
        try {
            SupplierEntity supplier = supplierRepository.findSupplierByIdAndIsDeletedFalse(id);
            if (supplier == null) {
                return Response.badRequest(404, "Supplier is not exist");
            }
            supplier.setIsDeleted(true);
            supplierRepository.save(supplier);
            return Response.ok(true);
        } catch (Exception e) {
            return Response.badRequest(500, e.getMessage());
        }
    }
}
