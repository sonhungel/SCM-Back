package com.scm.backend.repository;

import com.scm.backend.model.entity.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ItemTypeRepository extends JpaRepository<ItemType, Long>, QuerydslPredicateExecutor<ItemType> {
}
