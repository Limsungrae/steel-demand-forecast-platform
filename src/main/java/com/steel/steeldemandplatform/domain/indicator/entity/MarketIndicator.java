package com.steel.steeldemandplatform.domain.indicator.entity;

import com.steel.steeldemandplatform.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "market_indicator")
public class MarketIndicator extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 기준 연월
    @Column(nullable = false, unique = true)
    private String targetMonth;

    private Double autoProd;

    private Double autoDomesticShip;

    private Double autoExportShip;

    private Double constructionOrderAmt;

    private Double applianceProdIdx;

    private Double applianceShipIdx;

    private Double usdkrwAvg;

    private Double leadingIdx;

    private Double govBond3yAvg;

    private Double ironOrePrice;

    private Double steelCapacityIdx;

    private Double steelOperatingRate;
}