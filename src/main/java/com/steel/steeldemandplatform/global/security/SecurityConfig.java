package com.steel.steeldemandplatform.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // 회원가입 관련 경로 열어두기
                        .requestMatchers(
                                "/signup",
                                "/api/users/signup",
                                "/login" // 로그인 페이지 경로도 승인 목록에 추가해줍니다.
                        ).permitAll()

                        .anyRequest().permitAll()
                )

                // 🔴 이 부분을 Customizer.withDefaults() 대신 커스텀 설정으로 변경합니다.
                .formLogin(form -> form
                        .loginPage("/login")             // 내가 만든 로그인 "화면(HTML)"을 보여주는 URL 경로
                        .loginProcessingUrl("/login")    // HTML Form 태그의 action에 들어갈 로그인 "처리" URL 경로
                        .defaultSuccessUrl("/", true)    // 로그인 성공 시 이동할 메인 페이지 경로
                        .permitAll()
                );

        return http.build();
    }
}