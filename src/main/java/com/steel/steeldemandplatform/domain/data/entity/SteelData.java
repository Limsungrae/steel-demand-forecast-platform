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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private SteelItem steelItem;

    @Column(nullable = false)
    private String targetMonth;

    // 생산량
    private Double productionAmount;

    // 실제 수요량
    private Double demandAmount;

    // 재고량
    private Double inventoryAmount;

    // AI 예측 수요량
    private Double predictedDemand;

    // 안전재고
    private Double safetyStock;
}