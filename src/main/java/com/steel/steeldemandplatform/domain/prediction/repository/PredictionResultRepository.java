package com.steel.steeldemandplatform.domain.prediction.repository;

import com.steel.steeldemandplatform.domain.prediction.entity.PredictionResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PredictionResultRepository extends JpaRepository<PredictionResult, Long> {
}