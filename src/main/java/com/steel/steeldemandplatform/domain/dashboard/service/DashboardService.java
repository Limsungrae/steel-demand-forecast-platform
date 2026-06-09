package com.steel.steeldemandplatform.domain.dashboard.service;

import com.steel.steeldemandplatform.domain.data.entity.SteelData;
import com.steel.steeldemandplatform.domain.data.repository.SteelDataRepository;
import com.steel.steeldemandplatform.domain.dashboard.dto.DashboardSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // Repository를 자동으로 연결해주는 롬복 도구입니다.
public class DashboardService {

    private final SteelDataRepository steelDataRepository;

    /**
     * 특정 품목 코드와 원하는 달(Month)을 받아서 대시보드용 요약 데이터를 계산합니다.
     */
    public DashboardSummaryResponse getDashboardSummary(String itemCode, String targetMonth) {

        // 1. DB에서 품목코드(예: "CR")와 기준 연월(예: "26-Jan")이 모두 일치하는 데이터를 찾습니다.
        Optional<SteelData> oSteelData = steelDataRepository.findBySteelItem_ItemCodeAndTargetMonth(itemCode, targetMonth);

        // 만약 해당 날짜에 데이터가 없다면 모든 수치를 0으로 포장해서 안전하게 돌려보냅니다.
        if (oSteelData.isEmpty()) {
            return new DashboardSummaryResponse(0, 0, 0, "데이터 없음");
        }

        // 데이터가 존재하면 꺼냅니다.
        SteelData data = oSteelData.get();

        // 2. 소수점(Double) 데이터들을 계산하기 쉽게 정수(int)로 변환합니다. (데이터가 없으면 0 처리)
        int currentStock = data.getInventoryAmount() != null ? data.getInventoryAmount().intValue() : 0;
        int predictedDemand = data.getPredictedDemand() != null ? data.getPredictedDemand().intValue() : 0;
        int safetyStock = data.getSafetyStock() != null ? data.getSafetyStock().intValue() : 0;

        // 3. 부족 예상량 계산 공식: 현재 재고 - (예측 수요 + 안전 재고)
        // 계산 결과가 마이너스(-)면 재고가 부족하다는 뜻이 됩니다.
        int shortage = currentStock - (predictedDemand + safetyStock);

        // 4. 부족 예상량 수치에 따라 대시보드에 띄워줄 위험도(텍스트)를 판별합니다.
        String riskLevel;
        if (shortage < 0) {
            riskLevel = "부족위험";
        } else if (currentStock > (predictedDemand + safetyStock) * 1.5) {
            riskLevel = "과잉재고";
        } else {
            riskLevel = "정상";
        }

        // 5. 이 예쁜 결과물들을 DashboardSummaryResponse라는 봉투(DTO)에 담아서 반환합니다.
        return new DashboardSummaryResponse(predictedDemand, currentStock, shortage, riskLevel);
    }
}