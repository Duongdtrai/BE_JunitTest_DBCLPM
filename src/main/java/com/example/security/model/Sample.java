package com.example.security.model;

import jakarta.persistence.*;
import java.util.Date;
import lombok.Data;

@Entity
@Data
@Table(name = "sample")
public class Sample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "url")
    private String url;
    
    @Column(name = "alt")
    private String alt;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt", nullable = false, updatable = false)
    private Date createdAt;
    
    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}