package com.scm.backend.repository.custom;

public interface UserRoleCustomRepository {
    void deleteByKeyId(Long userId, Long roleId);
}
