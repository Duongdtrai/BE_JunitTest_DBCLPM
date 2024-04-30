package com.example.junit_test.modules.orders.entities;


import com.example.junit_test.base.entities.BaseEntity;
import com.example.junit_test.modules.suppliers.entities.Supplier;
import com.example.junit_test.modules.user.entities.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
  @Size(min = 5, max = 5, message = "Code must be exactly 5 characters")
  @Pattern(regexp = "[a-zA-Z]{2}[0-9]{3}", message = "Code must contain 2 letters followed by 3 numbers")
  private String code;

  @NotNull(message = "Tax must be not null")
  @PositiveOrZero(message = "Tax must be greater than 0")
  private Double tax;

  @Schema(hidden = true)
  private Double payment;

  @Column(name = "note", columnDefinition = "TEXT")
  private String note;

  @NotNull
  @Schema(hidden = true)
  private Boolean status = false;

  @NotNull
  @Positive
  @Column(name = "supplier_id", insertable = false, updatable = false)
//  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Integer supplierId;

  @ManyToOne
  @JoinColumn(name = "supplier_id")
  @Schema(hidden = true)
  private Supplier supplier;

  @OneToMany(mappedBy = "importOrder", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnoreProperties({"importOrder"})
  private List<ImportOrderProduct> importOrderProducts;

  @ManyToOne
  @JoinColumn(name = "employee_id")
  @Schema(hidden = true)
  private Employee employee;

  @NotNull
  @Positive
  @Column(name = "employee_id", insertable = false, updatable = false)
//  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Integer employeeId;


  @PrePersist
  protected void onCreate() {
    this.employee = new Employee();
    this.employee.setId(this.employeeId);

    this.supplier = new Supplier();
    this.supplier.setId(this.supplierId);
  }

  @PreUpdate
  protected void onUpdate() {
    this.employee = new Employee();
    this.employee.setId(this.employeeId);

    this.supplier = new Supplier();
    this.supplier.setId(this.supplierId);
  }
}
