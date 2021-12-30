package com.scm.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class InvoiceDetailKey implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "invoice_id")
    @JsonIgnore
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "item_id")
    @JsonIgnore
    private Item item;
}
