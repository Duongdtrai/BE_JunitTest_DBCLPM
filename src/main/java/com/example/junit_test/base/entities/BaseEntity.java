package com.example.junit_test.base.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @CreatedDate
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(name = "createdAt", nullable = false, updatable = false)
    private Date createdAt;

    @LastModifiedDate
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(name = "updatedAt")
    private Date updatedAt;

    @CreatedBy
    @Column(name = "createdBy")
    private User createdBy;

    @LastModifiedBy
    @Column(name = "updatedBy")
    private User updatedBy;

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

