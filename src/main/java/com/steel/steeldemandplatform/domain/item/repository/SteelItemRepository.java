package com.steel.steeldemandplatform.domain.item.repository;

import com.steel.steeldemandplatform.domain.item.entity.SteelItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SteelItemRepository extends JpaRepository<SteelItem, Long> {
}