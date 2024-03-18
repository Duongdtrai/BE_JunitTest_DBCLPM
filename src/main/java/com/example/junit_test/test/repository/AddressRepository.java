package com.example.junit_test.test.repository;

import com.example.junit_test.test.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository  extends JpaRepository<Address, Long> {
}
