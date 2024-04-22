package com.example.junit_test.modules.user.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tokens", uniqueConstraints = {
        @UniqueConstraint(columnNames = "token")
})
@Schema(description = "User Model Information")
public class Token {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Tutorial Id", example = "1")
  private Integer id;

  private String token;

  private Integer userId;
}
