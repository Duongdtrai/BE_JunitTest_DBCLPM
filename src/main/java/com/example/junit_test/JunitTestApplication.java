package com.example.junit_test;

import com.example.junit_test.test.entity.Address;
import com.example.junit_test.test.entity.Person;
import com.example.junit_test.test.repository.AddressRepository;
import com.example.junit_test.test.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class JunitTestApplication {
    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;

    public static void main(String[] args) {
        SpringApplication.run(JunitTestApplication.class, args);
    }
}
