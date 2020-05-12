package de.akdb.oesio.persistence.entities;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class ExampleDao {
    @PersistenceContext(unitName = "server-pu")
    private EntityManager em;

    protected ExampleDao(EntityManager em) {
        this.em = em;
    }

    public void persist(Object entity1, Object... furtherEntities) {
        List<Object> entities = new ArrayList<>(asList(furtherEntities));
        entities.add(0, entity1);

        entities.forEach((entity -> em.persist(entity)));
    }

    public <T> T get(Class<T> entityClass, Object id) {
        return em.find(entityClass, id);
    }
}
