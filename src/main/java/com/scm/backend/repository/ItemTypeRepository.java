package com.scm.backend.repository;

import com.scm.backend.model.entity.ItemType;
import com.scm.backend.util.ItemTypeState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemTypeRepository extends JpaRepository<ItemType, Long>, QuerydslPredicateExecutor<ItemType> {
    List<ItemType> findByState(ItemTypeState itemTypeState);
}
