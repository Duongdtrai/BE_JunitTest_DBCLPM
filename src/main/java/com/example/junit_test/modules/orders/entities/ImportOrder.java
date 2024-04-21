package com.example.junit_test.modules.orders.entities;


import com.example.junit_test.base.entities.BaseEntity;
import com.example.junit_test.modules.suppliers.entities.Supplier;
import com.example.junit_test.modules.user.entities.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class ImportOrder extends BaseEntity {
    @NotBlank
    @NotEmpty
    @NotNull
    private String code;

    private Double tax;

    @NotNull
    private Integer payment;


    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @NotNull
    private Boolean status = false;


    @ManyToOne
    @NotNull
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;


    @OneToMany(mappedBy = "importOrder",  cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"importOrder"})
    private List<ImportOrderProduct> importOrderProducts;


    @ManyToOne
    @NotNull
    @JoinColumn(name = "employee_id")
    private Employee employee;


}
