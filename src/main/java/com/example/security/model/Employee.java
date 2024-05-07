package com.example.security.model;

import jakarta.persistence.*;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Data
@Table(name = "employee")
public class Employee{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dob")
    private Date dob;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "description")
    private String description;
    
    @Column(name = "position")
    private String position;
    
    @OneToOne(mappedBy = "employee")
    private Sample sample;
}