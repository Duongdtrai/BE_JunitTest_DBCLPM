/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.security.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author bbuser
 */
@Entity
@Data
@Table(name = "EmployeeTest")
public class EmployeeTest {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "dob")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "position")
    private String position;
    
    @Column(name = "description")
    private String description;
}
