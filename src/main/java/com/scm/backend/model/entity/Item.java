package com.scm.backend.model.entity;

import com.scm.backend.util.ItemState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Item extends SupperEntity{

    @Column(unique = true, nullable = false)
    private Integer itemNumber;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String name;

    @Column(nullable = false, length = 3)
    @Enumerated(EnumType.STRING)
    private ItemState state;

    @Column(nullable = false)
    private LocalDate addedDate;

    @Column
    private LocalDate updateDate;
}
