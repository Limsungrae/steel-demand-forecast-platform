package com.steel.steeldemandplatform.domain.user.entity;

import com.steel.steeldemandplatform.global.common.BaseEntity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * 사용자 테이블
 *
 * 회원가입
 * 로그인
 * 소셜 로그인
 *
 * 에 사용
 */
@Entity

@Getter

@Builder

@NoArgsConstructor
@AllArgsConstructor

@Table(name = "users")
public class User extends BaseEntity {

    /*
     * PK
     */
    @Id

    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    /*
     * 사용자 이름
     */
    @Column(nullable = false)
    private String name;

    /*
     * 이메일
     *
     * 중복 불가
     */
    @Column(
            nullable = false,
            unique = true
    )
    private String email;

    /*
     * 암호화된 비밀번호 저장
     */
    @Column(nullable = false)
    private String password;

    /*
     * 회사명
     *
     * 예)
     * POSCO
     * 현대제철
     */
    private String companyName;

    /*
     * 권한
     *
     * ROLE_USER
     * ROLE_ADMIN
     */
    private String role;
}