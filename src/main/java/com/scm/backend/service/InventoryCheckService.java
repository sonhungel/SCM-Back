package com.scm.backend.service;

import com.scm.backend.model.dto.InventoryCheckDto;
import com.scm.backend.model.entity.InventoryCheck;
import com.scm.backend.model.exception.ItemNumberNotFoundException;

import java.util.List;

public interface InventoryCheckService {
    InventoryCheck createInventoryCheck(InventoryCheckDto inventoryCheckDto) throws ItemNumberNotFoundException;

    List<InventoryCheck> getInventoryChecks();
}
