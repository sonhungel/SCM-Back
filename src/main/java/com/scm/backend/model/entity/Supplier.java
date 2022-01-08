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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
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

    @Column
    private String email;

    @Column
    @Pattern(regexp="(^$|[0-9]{10})")
    @Size(min = 10, max = 11)
    private String phoneNumber;

    @Column
    private String taxNumber;

    @Column
    private Long type;

    @Column
    private String address;

    @Column
    private String province;

    @Column
    private String ward;

    @Column
    private String district;

    @Column
    private String remark;

    @Column
    private Long paid = 0L;

    @Column
    private LocalDate latestSupply;

    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
    private Set<Item> itemSet;
}
