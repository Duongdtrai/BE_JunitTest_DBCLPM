package com.example.junit_test.modules.orders.entities;

import com.example.junit_test.base.entities.BaseEntity;
import com.example.junit_test.modules.category.entities.Category;
import com.example.junit_test.modules.products.entities.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
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
    @NotNull
    @Positive
    private Integer quantity;

    @NotNull
    @PositiveOrZero
    private Integer importPrice;

    @NotNull
    @Positive
    @Column(name = "product_id", insertable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer productId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @Schema(hidden = true)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "import_order_id")
    @Schema(hidden = true)
    private ImportOrder importOrder;
}
