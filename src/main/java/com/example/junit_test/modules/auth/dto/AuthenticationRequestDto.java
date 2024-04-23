package com.example.junit_test.modules.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequestDto {
  @Email(message = "Email must be valid")
  @NotBlank
  @Size(max = 50)
  @Schema(description = "This is email", example = "ptd@gmail.com")
  private String email;

  @NotBlank
  @Schema(description = "This is password", example = "123123")
  private String password;

}
