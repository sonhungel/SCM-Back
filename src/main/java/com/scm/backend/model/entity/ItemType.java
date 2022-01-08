package com.scm.backend.model.entity;

import com.scm.backend.util.ItemTypeState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class ItemType extends SupperEntity {
    @Column(nullable = false, columnDefinition = "varchar(100)")
    private String typeName;

    @Column
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemTypeState state;

    @OneToMany(mappedBy = "itemType", fetch = FetchType.LAZY)
    private Set<Item> itemSet;
}
