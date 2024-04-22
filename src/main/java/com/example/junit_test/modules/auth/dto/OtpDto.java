package com.example.junit_test.modules.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpDto {
  @Email(message = "Email must be valid")
  @NotBlank
  @Size(max = 50)
  @Schema(description = "This is email", example = "ptd@gmail.com")
  private String email;

  @NotBlank
  @Size(max = 6)
  @Schema(description = "This is email", example = "123456")
  private String otp;
}
