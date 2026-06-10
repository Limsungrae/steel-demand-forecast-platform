package com.steel.steeldemandplatform.domain.indicator.repository;

import com.steel.steeldemandplatform.domain.indicator.entity.MarketIndicator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarketIndicatorRepository
        extends JpaRepository<MarketIndicator, Long> {

    Optional<MarketIndicator> findByTargetMonth(
            String targetMonth
    );
}