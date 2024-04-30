package com.example.junit_test.modules.orders.entities;

import com.example.junit_test.base.entities.BaseEntity;
import com.example.junit_test.modules.products.entities.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "import_order_product")
public class ImportOrderProduct extends BaseEntity {
  @NotNull(message = "Quantity is required")
  @Positive(message = "Quantity must be greater than 0")
  private Integer quantity;

  @NotNull(message = "Price is required")
  @Positive(message = "Price must be greater than 0")
  private Long importPrice;

  @NotNull
  @Positive
  @Column(name = "product_id", insertable = false, updatable = false)
//  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Integer productId;

  @ManyToOne
  @JoinColumn(name = "product_id")
  @Schema(hidden = true)
  private Product product;

  @ManyToOne
  @JoinColumn(name = "import_order_id")
  @Schema(hidden = true)
  private ImportOrder importOrder;

  @PrePersist
  protected void onCreate() {
    this.product = new Product();
    this.product.setId(this.productId);
  }

  @PreUpdate
  protected void onUpdate() {
    this.product = new Product();
    this.product.setId(this.productId);
  }
}
