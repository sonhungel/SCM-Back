package com.scm.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class InventoryCheck extends SupperEntity {
    @Column
    private String remark;

    @Column
    private Long availableQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "item_id")
    @JsonIgnore
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    @JsonIgnore
    private User user;
}
