package com.example.junit_test.modules.orders.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierOrderDto {
    @NotNull(message = "Id Supplier is required")
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "Id must be an integer")
    @Schema(description = "ID of the supplier", example = "1")
    private Integer id;
}
