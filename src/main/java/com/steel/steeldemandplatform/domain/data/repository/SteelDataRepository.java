package com.steel.steeldemandplatform.domain.data.repository;

import com.steel.steeldemandplatform.domain.data.entity.SteelData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SteelDataRepository extends JpaRepository<SteelData, Long> {
    // 이 한 줄을 추가하면 JPA가 품목코드와 기준월로 DB를 자동 검색해 줍니다!
    java.util.Optional<com.steel.steeldemandplatform.domain.data.entity.SteelData> findBySteelItem_ItemCodeAndTargetMonth(String itemCode, String targetMonth);
}