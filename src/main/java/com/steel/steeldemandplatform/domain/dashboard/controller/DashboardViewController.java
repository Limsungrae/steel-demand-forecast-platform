package com.steel.steeldemandplatform.domain.dashboard.controller;

import com.steel.steeldemandplatform.domain.dashboard.dto.DashboardSummaryResponse;
import com.steel.steeldemandplatform.domain.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller // 💡 RestController가 아니라 화면을 보여주는 'Controller'입니다!
@RequiredArgsConstructor
public class DashboardViewController {

    private final DashboardService dashboardService;

    /**
     * 웹 브라우저에 대시보드 HTML 화면을 띄워주는 매핑
     * URL: http://localhost:8080/dashboard?targetMonth=17-Jan
     */
    @GetMapping("/dashboard")
    public String dashboardPage(
            @RequestParam(value = "itemCode", defaultValue = "CR") String itemCode,
            @RequestParam(value = "targetMonth", defaultValue = "17-Jan") String targetMonth,
            Model model // 💡 HTML 파일에 데이터를 배달해주는 바구니입니다.
    ) {
        // 1. 우리가 만든 서비스에서 진짜 데이터를 뽑아옵니다.
        DashboardSummaryResponse summary = dashboardService.getDashboardSummary(itemCode, targetMonth);

        // 2. 바구니(Model)에 "summary"라는 이름으로 데이터를 담아서 HTML로 보냅니다.
        model.addAttribute("summary", summary);
        model.addAttribute("currentMonth", targetMonth); // 상단에 날짜 표시용

        // 3. resources/templates/dashboard.html 파일을 찾아서 띄우라는 뜻입니다.
        return "dashboard";
    }
}