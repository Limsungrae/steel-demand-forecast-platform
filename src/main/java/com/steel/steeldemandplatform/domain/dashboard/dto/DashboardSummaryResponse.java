package com.steel.steeldemandplatform.domain.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSummaryResponse {

    // 1. 예측 수요량 (predictedDemand)
    private int totalPredictedDemand;

    // 2. 현재 재고량 (inventoryAmount)
    private int totalCurrentStock;

    // 3. 부족 예상량 (현재재고 - 예측수요)
    private int totalShortage;

    // 4. 위험도 판단 결과 ("부족위험", "정상", "과잉재고")
    private String riskLevel;
}