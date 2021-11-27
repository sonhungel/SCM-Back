package com.scm.backend.repository;

import com.scm.backend.model.entity.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemTypeRepository extends JpaRepository<ItemType, Long>, QuerydslPredicateExecutor<ItemType> {
}
