package com.steel.steeldemandplatform.domain.prediction.entity;

import com.steel.steeldemandplatform.domain.item.entity.SteelItem;
import com.steel.steeldemandplatform.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "prediction_results")
public class PredictionResult extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 예측 대상 품목
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private SteelItem steelItem;

    // 예측 월
    private String targetMonth;

    // 예측 수요
    private Double predictedDemand;

    // 모델명
    private String modelName;
}