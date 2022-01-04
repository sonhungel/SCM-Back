package com.scm.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class InvoiceDetail {
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EmbeddedId
    private InvoiceDetailKey key;

    @Version
    private Integer version;

    @Column
    private LocalDate addedDate;

    @Column
    private LocalDate updateDate;

    @Column
    private Long price;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = true)
    private Double discount;
}
