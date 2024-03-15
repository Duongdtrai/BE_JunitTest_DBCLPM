package com.example.junit_test.repositories;

import com.example.junit_test.entities.SupplierEntity;
import io.micrometer.common.lang.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Integer> {
    SupplierEntity findSupplierById(Integer id);
}
