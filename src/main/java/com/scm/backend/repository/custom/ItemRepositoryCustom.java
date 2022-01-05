package com.scm.backend.repository.custom;

import com.scm.backend.model.entity.Item;

import java.util.List;

public interface ItemRepositoryCustom {
    List<Item> findItemsQuery(String condition);
}
