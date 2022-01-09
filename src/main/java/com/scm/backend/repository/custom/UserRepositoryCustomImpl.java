package com.scm.backend.repository.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.scm.backend.model.entity.*;

import java.util.List;

public class UserRepositoryCustomImpl extends SupperRepositoryCustom implements UserRepositoryCustom {

    @Override
    public List<User> getAllUser() {
        QUser user = QUser.user;

        BooleanBuilder builder = new BooleanBuilder();

        return new JPAQuery<User>(em)
                .from(QUser.user)
                .leftJoin(QUser.user.userRoleList, QUserRole.userRole)
                .fetchJoin()
                .leftJoin(QUserRole.userRole.key.role, QRole.role)
                .fetchJoin()
                .leftJoin(QRole.role.rolePermissionList, QRolePermission.rolePermission)
                .fetchJoin()
                .leftJoin(QRolePermission.rolePermission.key.permission, QPermission.permission)
                .fetchJoin()
                .where(builder)
                .orderBy(QUser.user.username.asc())
                .fetchAll().distinct().fetch();
    }
}
