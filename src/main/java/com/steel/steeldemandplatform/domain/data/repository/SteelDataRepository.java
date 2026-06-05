package com.steel.steeldemandplatform.domain.data.repository;

import com.steel.steeldemandplatform.domain.data.entity.SteelData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SteelDataRepository extends JpaRepository<SteelData, Long> {
}