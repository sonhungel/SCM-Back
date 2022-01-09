package com.scm.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scm.backend.util.InvoiceState;
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
public class Invoice extends SupperEntity {
    @Column
    private Long paid = 0L;

    @Column
    @Enumerated(EnumType.STRING)
    private InvoiceState status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "customer_id")
    @JsonIgnore
    private Customer customer;

}
