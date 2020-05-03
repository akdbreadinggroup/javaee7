package de.akdb.oesio.persistence.entities;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toSet;
import static javax.persistence.Persistence.createEntityManagerFactory;
import static org.assertj.core.api.Assertions.assertThat;

class ExampleDaoTest {
    private EntityManagerFactory testEmFactory;
    private EntityManager testEm;
    private ExampleDao dao;

    @BeforeEach
    void setUp() {
        createEntityManager();
        dao = new ExampleDao(testEm);
    }

    @AfterEach
    void tearDown() {
        cleanupEntityManager();
    }

    @Test
    void should_persist_and_return_simple_entity_to_and_from_DB() {
        PropertiesOnlyMinimalFieldAnnotations entity = new PropertiesOnlyMinimalFieldAnnotations();
        entity.setName("testName");

        runInTransaction(() -> dao.persist(entity));

        PropertiesOnlyMinimalFieldAnnotations readFromDb = dao
                .get(PropertiesOnlyMinimalFieldAnnotations.class, entity.getId());

        assertThat(readFromDb).isEqualToComparingFieldByField(entity);
    }

    @Test
    void should_persist_and_return_OneToMany_relation_for_entity_with_generated_id() {
        GeneratedIdParent parent = new GeneratedIdParent();
        GeneratedIdChild child1 = new GeneratedIdChild();
        child1.setName("child1");
        GeneratedIdChild child2 = new GeneratedIdChild();
        child2.setName("child2");
        parent.addChild(child1);
        parent.addChild(child2);

        Map<GeneratedIdChild, String> children2Name = new HashMap<>();
        children2Name.put(child1, child1.getName());
        children2Name.put(child2, child2.getName());

        runInTransaction(() -> dao.persist(parent));

        GeneratedIdParent readFromDb = dao
                .get(GeneratedIdParent.class, parent.getId());

        assertThat(readFromDb).isEqualToComparingFieldByField(parent);
        assertThat(readFromDb.getChildren().stream()
                .map(GeneratedIdChild::getName).collect(toSet()))
                .containsExactlyInAnyOrderElementsOf(children2Name.values());
        assertThat(readFromDb.getChildren()).containsExactlyInAnyOrder(child1, child2);
        assertThat(readFromDb.getChildren().contains(child1)).isTrue();
        assertThat(readFromDb.getChildren().contains(child2)).isTrue();

        assertThat(children2Name.get(child1)).isEqualTo("child1");
        assertThat(children2Name.get(child2)).isEqualTo("child2");
    }

    @Test
    void should_persist_and_return_OneToMany_relation_for_entity_with_UUID() {
        UuidParent parent = new UuidParent();
        UuidChild child1 = new UuidChild();
        child1.setName("child1");
        UuidChild child2 = new UuidChild();
        child2.setName("child2");
        parent.addChild(child1);
        parent.addChild(child2);

        Map<UuidChild, String> children2Name = new HashMap<>();
        children2Name.put(child1, child1.getName());
        children2Name.put(child2, child2.getName());

        runInTransaction(() -> dao.persist(parent));

        UuidParent readFromDb = dao
                .get(UuidParent.class, parent.getId());

        assertThat(readFromDb).isEqualToComparingFieldByField(parent);
        assertThat(readFromDb.getChildren().stream()
                .map(UuidChild::getName).collect(toSet()))
                .containsExactlyInAnyOrderElementsOf(children2Name.values());
        assertThat(readFromDb.getChildren()).containsExactlyInAnyOrder(child1, child2);
        assertThat(readFromDb.getChildren().contains(child1)).isTrue();
        assertThat(readFromDb.getChildren().contains(child2)).isTrue();

        assertThat(children2Name.get(child1)).isEqualTo("child1");
        assertThat(children2Name.get(child2)).isEqualTo("child2");
    }

    private void runInTransaction(Runnable runnable) {
        runInTransaction(() -> {
            runnable.run();
            return null;
        });
    }

    @SuppressWarnings("UnusedReturnValue")
    private <T> T runInTransaction(Supplier<T> supplier) {
        EntityTransaction transaction = testEm.getTransaction();
        transaction.begin();
        try {
            T result = supplier.get();
            transaction.commit();
            return result;
        } catch (RuntimeException ex) {
            transaction.rollback();
            throw ex;
        } finally {
            testEm.clear();
        }
    }

    private void createEntityManager() {
        testEmFactory = createEntityManagerFactory("entities-pu");
        testEm = testEmFactory.createEntityManager();
    }

    private void cleanupEntityManager() {
        if (testEm != null) {
            testEm.close();
            testEm = null;
        }
        if (testEmFactory != null) {
            testEmFactory.close();
            testEmFactory = null;
        }
    }
}