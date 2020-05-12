package de.akdb.oesio.persistence.entities;

import org.assertj.core.api.ThrowableAssert;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import java.util.function.Supplier;

import static javax.persistence.Persistence.createEntityManagerFactory;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AbstractExampleDaoTest {
    private EntityManager testEm;
    protected ExampleDao dao;
    private EntityManagerFactory testEmFactory;

    protected AbstractExampleDaoTest(String pu) {
        this.pu = pu;
    }

    private String pu;

    @BeforeEach
    void setUp() {
        createEntityManager();
        dao = new ExampleDao(testEm);
    }

    @AfterEach
    void tearDown() {
        cleanupEntityManager();
    }

    private void createEntityManager() {
        testEmFactory = createEntityManagerFactory(pu);
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

    protected void runInTransaction(Runnable runnable) {
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

    protected void assertLazyInitialized(ThrowableAssert.ThrowingCallable check) {
        testEm.clear();
        assertThatThrownBy(check)
                .isInstanceOf(LazyInitializationException.class);
    }
}
