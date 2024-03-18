package com.example.junit_test.modules.suppliers.repositories;

import com.example.junit_test.modules.suppliers.entities.SupplierEntity;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Integer> {
    SupplierEntity findSupplierByIdAndIsDeletedFalse(Integer id);
    SupplierEntity findByNameAndAddressAndPhoneNumber(String name, @NotBlank(message = "Address is required") String address, @NotBlank(message = "Phone number is required") String phoneNumber);
}
