package com.steel.steeldemandplatform.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

/*
 * 회원가입 요청 DTO
 */
@Getter
@Setter
public class SignupRequest {

    private String name;

    private String email;

    private String password;

    private String companyName;

}