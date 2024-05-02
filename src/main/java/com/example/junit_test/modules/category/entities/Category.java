package com.example.junit_test.modules.category.entities;

import com.example.junit_test.base.entities.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Table(name = "categories")
public class Category extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(hidden = true)
  private Integer id;

  @NotEmpty
  @NotBlank
//  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String name;

  @Schema(hidden = true)
  private Boolean isDeleted = false;

  @PrePersist
  protected void onCreate() {
    // Thực hiện các thao tác trước khi đối tượng được lưu vào cơ sở dữ liệu
    System.out.println("name: " + name);
  }

  @PreUpdate
  protected void onUpdate() {
    System.out.println("name 1: " + name);
    // Thực hiện các thao tác trước khi đối tượng được cập nhật trong cơ sở dữ liệu
  }

  @PostPersist
  protected void onPersist() {
    System.out.println("name 2: " + name);
    // Thực hiện các thao tác sau khi đối tượng được lưu vào cơ sở dữ liệu
  }

  @PostUpdate
  protected void onPostUpdate() {
    System.out.println("name 3: " + name);
    // Thực hiện các thao tác sau khi đối tượng được cập nhật trong cơ sở dữ liệu
  }


  @PostRemove
  protected void onRemove() {
    System.out.println("name 4: " + name);
    // Thực hiện các thao tác sau khi đối tượng bị xóa khỏi cơ sở dữ liệu
  }
}
