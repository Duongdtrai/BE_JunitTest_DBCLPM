package com.example.junit_test.modules.orders.entities;


import com.example.junit_test.base.entities.BaseEntity;
import com.example.junit_test.modules.category.entities.Category;
import com.example.junit_test.modules.suppliers.entities.Supplier;
import com.example.junit_test.modules.user.entities.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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


    @NotNull
    @Positive
    @Column(name = "supplier_id", insertable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer supplierId;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    @Schema(hidden = true)
    private Supplier supplier;

    @OneToMany(mappedBy = "importOrder",  cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"importOrder"})
    private List<ImportOrderProduct> importOrderProducts;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "employee_id")
    @Schema(hidden = true)
    private Employee employee;

    @NotNull
    @Positive
    @Column(name = "employee_id", insertable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer employeeId;

}
