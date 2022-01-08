package com.scm.backend.model.entity;

import com.scm.backend.util.ItemState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Item extends SupperEntity {
    @Column(unique = true, nullable = false)
    private Integer itemNumber;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemState state;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private Long availableQuantity;

    @Column(nullable = false)
    private Long minimumQuantity;

    // gia ban
    @Column(nullable = false)
    private Long salesPrice;

    // gia von
    @Column(nullable = false)
    private Long cost;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private String remark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true, name = "itemType_id")
    private ItemType itemType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true, name = "supplier_id")
    private Supplier supplier;

}
