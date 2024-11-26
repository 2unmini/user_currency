package com.sparta.currency_user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Getter
@MappedSuperclass
public class BaseEntity { // Entity의 공통적으로 들어가는 필드
    @CreatedDate
    @Column(name = "created_at" ,updatable = false)
    private LocalDateTime createdAt; // 생성 일시
    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt; // 수정 일시
}
