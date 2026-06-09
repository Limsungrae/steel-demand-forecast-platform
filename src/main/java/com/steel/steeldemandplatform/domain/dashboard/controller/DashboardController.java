package com.steel.steeldemandplatform.domain.dashboard.controller;

import com.steel.steeldemandplatform.domain.dashboard.dto.DashboardSummaryResponse;
import com.steel.steeldemandplatform.domain.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor // DashboardService를 스프링이 자동으로 주입(연결)해줍니다.
public class DashboardController {

    private final DashboardService dashboardService;

    /**
     * 대시보드 상단 핵심 지표 요약 API
     * URL 예시: GET /api/dashboard/summary?itemCode=CR&targetMonth=17-Jan
     */
    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryResponse> getDashboardSummary(
            @RequestParam(value = "itemCode", defaultValue = "CR") String itemCode,
            @RequestParam(value = "targetMonth", defaultValue = "17-Jan") String targetMonth
    ) {
        // 우리가 열심히 만든 서비스 로직을 호출하여 진짜 DB 데이터를 계산합니다.
        DashboardSummaryResponse summary = dashboardService.getDashboardSummary(itemCode, targetMonth);

        return ResponseEntity.ok(summary);
    }
}