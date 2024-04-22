package com.example.junit_test.modules.suppliers.repositories;

import com.example.junit_test.modules.suppliers.entities.Supplier;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
  Page<Supplier> findAllByIsDeletedFalse(Pageable pageable);

  Supplier findSupplierByIdAndIsDeletedFalse(Integer id);

  Supplier findByNameAndAddressAndPhoneNumber(String name, @NotBlank(message = "Address is required") String address, @NotBlank(message = "Phone number is required") String phoneNumber);

  @Transactional
  @Modifying
  @Query("UPDATE Supplier e SET e.isDeleted = true WHERE e.id IN :ids")
  void deleteAllById(List<Integer> ids);

  Integer countAllByIdIn(List<Integer> ids);

}
