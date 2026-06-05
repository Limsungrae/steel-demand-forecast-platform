package com.steel.steeldemandplatform.global.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

/*
 * 모든 엔티티가 공통으로 사용할
 * 생성일(createdAt)
 * 수정일(updatedAt)
 * 관리 클래스
 */
@Getter

// 테이블로 생성되지 않고
// 상속용 클래스라는 의미
@MappedSuperclass

// JPA가 생성일/수정일 자동 관리
@EntityListeners(
        org.springframework.data.jpa.domain.support.AuditingEntityListener.class
)
public abstract class BaseEntity {

    /*
     * 데이터 최초 생성 시간
     */
    @CreatedDate

    // 생성 이후 수정 불가
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /*
     * 데이터 수정 시간
     */
    @LastModifiedDate
    private LocalDateTime updatedAt;
}