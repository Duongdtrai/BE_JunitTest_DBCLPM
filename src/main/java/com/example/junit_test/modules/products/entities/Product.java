package com.example.junit_test.modules.products.entities;


import com.example.junit_test.base.entities.BaseEntity;
import com.example.junit_test.modules.category.entities.Category;
import com.example.junit_test.modules.orders.entities.ImportOrderProduct;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "products")
@JsonIgnoreProperties({"importOrderProducts"})
public class Product extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Schema(hidden = true)
    private Integer id;

    private String image;

    @NotBlank(message = "Name is required")
    @NotEmpty
    @NotNull
    private String name;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be greater than 0")
    private Integer quantity;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private double price;

    private String description;

    @Column(name = "isDeleted", columnDefinition = "BOOLEAN DEFAULT false")
    @Schema(hidden = true)
    private Boolean isDeleted = false;

    @NotNull
    @Positive
    @Column(name = "category_id", insertable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer categoryId;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @Schema(hidden = true)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"importOrder", "product"})
    private List<ImportOrderProduct> importOrderProducts;

    @PrePersist
    protected void onCreate() {
        this.category = new Category();
        this.category.setId(this.categoryId);
    }

    @PreUpdate
    protected void onUpdate() {
        this.category = new Category();
        this.category.setId(this.categoryId);
    }
}
