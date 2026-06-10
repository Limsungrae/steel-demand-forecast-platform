package com.steel.steeldemandplatform.domain.data.service;

import com.opencsv.CSVReader;
import com.steel.steeldemandplatform.domain.data.entity.SteelData;
import com.steel.steeldemandplatform.domain.data.repository.SteelDataRepository;
import com.steel.steeldemandplatform.domain.indicator.entity.MarketIndicator;
import com.steel.steeldemandplatform.domain.indicator.repository.MarketIndicatorRepository;
import com.steel.steeldemandplatform.domain.item.entity.SteelItem;
import com.steel.steeldemandplatform.domain.item.repository.SteelItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;

@Service
@RequiredArgsConstructor
public class CsvUploadService {

    private final SteelDataRepository steelDataRepository;
    private final SteelItemRepository steelItemRepository;
    private final MarketIndicatorRepository marketIndicatorRepository;

    public void upload(MultipartFile file) {

        try (
                CSVReader reader =
                        new CSVReader(
                                new InputStreamReader(
                                        file.getInputStream()
                                )
                        )
        ) {

            // 헤더 제거
            reader.readNext();

            SteelItem hr =
                    steelItemRepository
                            .findByItemCode("HR")
                            .orElseThrow(() ->
                                    new RuntimeException("HR 품목 없음"));

            SteelItem cr =
                    steelItemRepository
                            .findByItemCode("CR")
                            .orElseThrow(() ->
                                    new RuntimeException("CR 품목 없음"));

            SteelItem gi =
                    steelItemRepository
                            .findByItemCode("GI")
                            .orElseThrow(() ->
                                    new RuntimeException("GI 품목 없음"));

            String[] row;

            while ((row = reader.readNext()) != null) {

                if (row.length < 22) {
                    continue;
                }

                String targetMonth = row[0].trim();

                // =========================
                // HR
                // =========================
                saveSteelData(
                        hr,
                        targetMonth,
                        getValue(row, 1),
                        getValue(row, 2),
                        getValue(row, 3)
                );

                // =========================
                // CR
                // =========================
                saveSteelData(
                        cr,
                        targetMonth,
                        getValue(row, 4),
                        getValue(row, 5),
                        getValue(row, 6)
                );

                // =========================
                // GI
                // =========================
                saveSteelData(
                        gi,
                        targetMonth,
                        getValue(row, 7),
                        getValue(row, 8),
                        getValue(row, 9)
                );

                // 이미 저장된 지표면 패스
                if (marketIndicatorRepository
                        .findByTargetMonth(targetMonth)
                        .isPresent()) {

                    continue;
                }

                MarketIndicator indicator =
                        MarketIndicator.builder()
                                .targetMonth(targetMonth)

                                .autoProd(
                                        parseDouble(getValue(row, 10))
                                )
                                .autoDomesticShip(
                                        parseDouble(getValue(row, 11))
                                )
                                .autoExportShip(
                                        parseDouble(getValue(row, 12))
                                )
                                .constructionOrderAmt(
                                        parseDouble(getValue(row, 13))
                                )
                                .applianceProdIdx(
                                        parseDouble(getValue(row, 14))
                                )
                                .applianceShipIdx(
                                        parseDouble(getValue(row, 15))
                                )
                                .usdkrwAvg(
                                        parseDouble(getValue(row, 16))
                                )
                                .leadingIdx(
                                        parseDouble(getValue(row, 17))
                                )
                                .govBond3yAvg(
                                        parseDouble(getValue(row, 18))
                                )
                                .ironOrePrice(
                                        parseDouble(getValue(row, 19))
                                )
                                .steelCapacityIdx(
                                        parseDouble(getValue(row, 20))
                                )
                                .steelOperatingRate(
                                        parseDouble(getValue(row, 21))
                                )
                                .build();

                marketIndicatorRepository.save(indicator);
            }

            System.out.println("=================================");
            System.out.println("CSV 업로드 완료");
            System.out.println("=================================");

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "CSV 업로드 실패",
                    e
            );
        }
    }

    private void saveSteelData(
            SteelItem item,
            String targetMonth,
            String production,
            String demand,
            String inventory
    ) {

        boolean exists =
                steelDataRepository
                        .findBySteelItem_ItemCodeAndTargetMonth(
                                item.getItemCode(),
                                targetMonth
                        )
                        .isPresent();

        if (exists) {
            return;
        }

        SteelData steelData =
                SteelData.builder()
                        .steelItem(item)
                        .targetMonth(targetMonth)

                        .productionAmount(
                                parseDouble(production)
                        )

                        .demandAmount(
                                parseDouble(demand)
                        )

                        .inventoryAmount(
                                parseDouble(inventory)
                        )

                        // AI 모델 연결 전 기본값
                        .predictedDemand(0.0)

                        // 안전재고 기본값
                        .safetyStock(0.0)

                        .build();

        steelDataRepository.save(steelData);
    }

    private String getValue(
            String[] row,
            int index
    ) {

        if (index >= row.length) {
            return "";
        }

        return row[index];
    }

    private Double parseDouble(String value) {

        if (value == null ||
                value.trim().isEmpty()) {

            return 0.0;
        }

        try {

            return Double.parseDouble(
                    value.replace(",", "")
                            .trim()
            );

        } catch (Exception e) {

            return 0.0;
        }
    }
}