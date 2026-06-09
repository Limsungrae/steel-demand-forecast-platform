package com.steel.steeldemandplatform.domain.data.entity;

import com.steel.steeldemandplatform.domain.item.entity.SteelItem;
import com.steel.steeldemandplatform.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "steel_data")
public class SteelData extends BaseEntity {

    // 데이터 PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 품목
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private SteelItem steelItem;

    // 기준 연월
    // 예: 2025-01
    @Column(nullable = false)
    private String targetMonth;

    // 생산량
    private Double productionAmount;

    // 국내 판매량
    private Double domesticSales;

    // 수출량
    private Double exportAmount;

    // 재고량
    private Double inventoryAmount;
    // 예측 수요량
    private Double predictedDemand;

    // 안전 재고량
    private Double safetyStock;
}