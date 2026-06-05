package com.steel.steeldemandplatform.domain.item.entity;

import com.steel.steeldemandplatform.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "steel_items")
public class SteelItem extends BaseEntity {

    // 품목 PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 품목 코드
    // HR : 열연강판
    // CR : 냉연강판
    // GI : 아연도강판
    @Column(nullable = false, unique = true)
    private String itemCode;

    // 품목명
    @Column(nullable = false)
    private String itemName;
}