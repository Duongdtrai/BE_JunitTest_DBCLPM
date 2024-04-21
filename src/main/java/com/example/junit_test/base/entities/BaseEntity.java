package com.example.junit_test.base.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private Integer id;

    @CreatedDate
    @Column(name = "createdAt", nullable = false, updatable = false)
    @Schema(hidden = true)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updatedAt")
    @Schema(hidden = true)
    private Date updatedAt;

//    @PrePersist
//    protected void prePersist() {
//        createdAt = new Date();
//        updatedAt = new Date();
//    }


////    @PrePersist
////    protected void onCreate() {
////        // Thực hiện các thao tác trước khi đối tượng được lưu vào cơ sở dữ liệu
////    }
////
//    @PreUpdate
//    protected void onUpdate() {
//        updatedAt = new Date();
//        // Thực hiện các thao tác trước khi đối tượng được cập nhật trong cơ sở dữ liệu
//    }
////
////    @PostPersist
////    protected void onPersist() {
////        // Thực hiện các thao tác sau khi đối tượng được lưu vào cơ sở dữ liệu
////    }
////
////    @PostUpdate
////    protected void onPostUpdate() {
////        // Thực hiện các thao tác sau khi đối tượng được cập nhật trong cơ sở dữ liệu
////    }
////
////
////    @PostRemove
////    protected void onRemove() {
////        // Thực hiện các thao tác sau khi đối tượng bị xóa khỏi cơ sở dữ liệu
////    }
}

