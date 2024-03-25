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
    @NotNull(message = "Id nhà cung cấp phải tồn tại")
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "id là dạng số")
    @Schema(description = "ID of the supplier", example = "1")
    private Integer id;
}
