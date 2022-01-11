package com.scm.backend.repository;

import com.scm.backend.model.entity.Role;
import com.scm.backend.model.entity.Supplier;
import com.scm.backend.repository.custom.RoleRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, QuerydslPredicateExecutor<Role>, RoleRepositoryCustom {
    List<Role> findByName(String name);
}
