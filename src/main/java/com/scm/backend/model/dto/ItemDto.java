package com.scm.backend.model.dto;

import com.scm.backend.util.ItemState;
import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class ItemDto extends SupperDto{

    @EqualsAndHashCode.Include
    @Digits(integer = 4, fraction = 0)
    private Integer itemNumber;

    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @NotNull
    private ItemState state;

    @NotNull
    private LocalDate addedDate;

    private LocalDate updateDate;

    @NotNull
    private Long quantity;

    @NotNull
    private Long salesPrice;

    @NotNull
    private Long cost;

    private ItemTypeDto itemType;
}
