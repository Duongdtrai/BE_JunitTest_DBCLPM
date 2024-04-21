package com.example.junit_test.modules.user.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @NotEmpty
    @NotNull
    private String name;

    @Column(name = "username")
    @NotBlank
    @NotNull
    private String username;

    @NotEmpty
    @NotBlank
    @NotNull
    private String password;

    @NotEmpty
    @NotBlank
    @NotNull
    private String phoneNumber;

    @NotNull
    private Date dateOfBirth;

    @NotNull
    private String address;

    @NotNull
    private String homeTown;

    @NotNull
    private String gender;

//    @OneToMany(mappedBy = "employee")
//    private List<ImportOrder> importOrders;
}
