package com.example.junit_test.modules.user.entities;

import com.example.junit_test.base.entities.BaseEntity;
import com.example.junit_test.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")
})
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotBlank
    private String email;

    @Column(name = "username")
    @NotBlank
    @NotBlank
    private String username;

    @NotEmpty
    @NotBlank
    private String password;
    private Role role;
}
