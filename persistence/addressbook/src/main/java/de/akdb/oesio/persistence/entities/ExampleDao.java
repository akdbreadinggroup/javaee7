package de.akdb.oesio.persistence.entities;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ExampleDao {
    @PersistenceContext(unitName = "server-pu")
    private EntityManager em;

    ExampleDao(EntityManager em) {
        this.em = em;
    }

    public void persist(Object entity) {
        em.persist(entity);
    }

    public <T> T get(Class<T> entityClass, Object id) {
        return em.find(entityClass, id);
    }
}
