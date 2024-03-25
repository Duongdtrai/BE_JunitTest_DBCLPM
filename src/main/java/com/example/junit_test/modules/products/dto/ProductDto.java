package com.example.junit_test.modules.products.dto;

import com.example.junit_test.modules.category.entities.CategoryEntity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String image;
    @NotBlank(message = "Tên là bắt buộc")
    private String name;
    @NotNull(message = "Số lượng là bắt buộc")
    private Integer quantity;
    @NotNull(message = "Bắt buộc phải có giá")
    private double price;
    private String description;
    private CategoryProductDto category;
}
