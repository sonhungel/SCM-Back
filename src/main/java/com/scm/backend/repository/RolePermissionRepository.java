package com.scm.backend.repository;

import com.scm.backend.model.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long>, QuerydslPredicateExecutor<RolePermission> {
    List<RolePermission> findByKey_Role_Id(Long roleId);
}
