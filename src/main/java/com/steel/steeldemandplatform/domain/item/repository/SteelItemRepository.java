package com.steel.steeldemandplatform.domain.item.repository;

import com.steel.steeldemandplatform.domain.item.entity.SteelItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SteelItemRepository
        extends JpaRepository<SteelItem, Long> {
    Optional<SteelItem> findByItemCode(String itemCode);
}