package com.example.junit_test.modules.suppliers.entities;


import com.example.junit_test.base.entities.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "suppliers")
public class Supplier extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(hidden = true)
  private Integer id;

  @Email
  @NotBlank(message = "Email is required")
  @NotNull
  private String email;

  @NotBlank(message = "Name is required")
  private String name;

  @NotBlank
  @NotEmpty
  @NotNull
  private String taxCode;

  @NotBlank(message = "Address is required")
  private String address;

  @NotBlank(message = "Số điện thoại không được để trống")
  @Pattern(regexp = "^\\+?[0-9]{10}$", message = "Số điện thoại không hợp lệ")
  private String phoneNumber;

  @Column(name = "note", columnDefinition = "TEXT", nullable = false)
  private String note;

  @Column(name = "isDeleted", columnDefinition = "BOOLEAN DEFAULT false")
  @Schema(hidden = true)
  private Boolean isDeleted = false;

//    @JsonIgnore
//    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
//    private List<OrderEntity> orders
}
