package com.steel.steeldemandplatform.global.config;

import com.steel.steeldemandplatform.domain.data.entity.SteelData;
import com.steel.steeldemandplatform.domain.data.repository.SteelDataRepository;
import com.steel.steeldemandplatform.domain.item.entity.SteelItem;
import com.steel.steeldemandplatform.domain.item.repository.SteelItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(
            SteelItemRepository itemRepository,
            SteelDataRepository dataRepository
    ) {
        return args -> {
            // 1. 냉연강판(CR) 품목 마스터가 DB에 있는지 확인하고 없으면 만듭니다.
            String itemCode = "CR";
            SteelItem coldRolledSteel;
            Optional<SteelItem> existingItem = itemRepository.findByItemCode(itemCode);

            if (existingItem.isPresent()) {
                coldRolledSteel = existingItem.get();
            } else {
                coldRolledSteel = SteelItem.builder()
                        .itemCode(itemCode)
                        .itemName("냉연강판")
                        .build();
                itemRepository.save(coldRolledSteel);
            }

            // 2. resources 폴더에 넣은 CSV 파일을 읽어옵니다.
            ClassPathResource resource = new ClassPathResource("cr_steel_history.csv");

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

                String line;
                boolean isHeader = true; // 첫 번째 줄(컬럼명 헤더) 건너뛰기용

                while ((line = br.readLine()) != null) {
                    if (isHeader) {
                        isHeader = false;
                        continue;
                    }

                    // 콤마(,)를 기준으로 한 줄의 데이터를 쪼갭니다.
                    String[] tokens = line.split(",");
                    String dateStr = tokens[0].trim(); // 예: "17-Jan"

                    // 🔴 [안전장치] 이미 DB에 동일한 품목과 날짜의 데이터가 있으면 패스해서 에러를 방지합니다.
                    boolean isDuplicate = dataRepository.findBySteelItem_ItemCodeAndTargetMonth(coldRolledSteel.getItemCode(), dateStr).isPresent();
                    if (isDuplicate) {
                        continue;
                    }

                    // 사진 순서: date, steel_prod, steel_dem, steel_inv
                    Double production = parseDoubleSafely(tokens[1]); // 생산량
                    Double demand = parseDoubleSafely(tokens[2]);     // 실제 수요량
                    Double inventory = parseDoubleSafely(tokens[3]);  // 재고량

                    // 3. 데이터 저장
                    SteelData steelData = SteelData.builder()
                            .steelItem(coldRolledSteel)
                            .targetMonth(dateStr)
                            .productionAmount(production)
                            .inventoryAmount(inventory)
                            .domesticSales(0.0)
                            .exportAmount(0.0)
                            .predictedDemand(demand) // 예측 모델 전까지 실제 수요 대입
                            .safetyStock(0.0)
                            .build();

                    dataRepository.save(steelData);
                }

                System.out.println("=================================================");
                System.out.println(">> [성공] CSV 파일 데이터를 깔끔하게 읽어 DB에 저장했습니다!");
                System.out.println("=================================================");

            } catch (Exception e) {
                System.out.println(">> [에러 발생] CSV 파일을 읽는 중 오류가 발생했습니다: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }

    private Double parseDoubleSafely(String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0.0;
        }
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}