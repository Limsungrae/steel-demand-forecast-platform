package com.steel.steeldemandplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/*
 * JPA Auditing 활성화
 *
 * createdAt
 * updatedAt
 * 자동 저장
 */
@EnableJpaAuditing

@SpringBootApplication
public class SteelDemandPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                SteelDemandPlatformApplication.class,
                args
        );
    }
}