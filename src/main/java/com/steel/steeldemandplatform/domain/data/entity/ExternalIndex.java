package com.steel.steeldemandplatform.domain.data.entity;

import com.steel.steeldemandplatform.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "external_indices")
public class ExternalIndex extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 기준 연월
    private String targetMonth;

    // 건설수주액
    private Double constructionOrders;

    // 건설기성액
    private Double constructionCompleted;

    // 제조업 생산지수
    private Double manufacturingIndex;

    // 자동차 생산지수
    private Double automobileIndex;
}