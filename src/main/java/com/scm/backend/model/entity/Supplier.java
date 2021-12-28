package com.scm.backend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Supplier extends SupperEntity {
    @Column(unique = true, nullable = false)
    private Integer supplierNumber;

    @Column(nullable = false, columnDefinition = "varchar(100)")
    private String name;

    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
    private Set<Item> itemSet;
}
