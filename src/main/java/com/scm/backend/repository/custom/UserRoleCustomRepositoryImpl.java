package com.scm.backend.repository.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.scm.backend.model.entity.*;

public class UserRoleCustomRepositoryImpl extends SupperRepositoryCustom implements UserRoleCustomRepository {
    @Override
    public void deleteByKeyId(Long userId, Long roleId) {

    }
}
