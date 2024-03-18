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
public class JunitTestApplication implements CommandLineRunner {
    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;

    public static void main(String[] args) {
        SpringApplication.run(JunitTestApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Tạo đối tượng Address và liên kết với nhiều Person
        Address address = Address.builder()
                .city("New York")
                .province("NY")
                .build();

        Person person1 = Person.builder()
                .name("John Doe")
                .address(address)
                .build();

        Person person2 = Person.builder()
                .name("Jane Doe")
                .address(address)
                .build();
        address.setPersons(List.of(person1, person2));
        // Lưu vào db
        addressRepository.save(address);
    }
}
