package com.scm.backend.repository.custom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class SupperRepositoryCustom {
    @PersistenceContext
    EntityManager em;
}
