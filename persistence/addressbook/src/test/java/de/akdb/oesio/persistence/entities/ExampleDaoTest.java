package de.akdb.oesio.persistence.entities;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExampleDaoTest extends AbstractExampleDaoTest {

    ExampleDaoTest() {
        super("entities-pu");
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

        runInTransaction(() -> dao.persist(parent));

        assertThat(parent.getChildren()).hasSize(2);
        assertThat(parent.getChildren()).containsExactlyInAnyOrder(child1, child2);
        // pitfall when hashCode is changed by persisting entity
        // directly use contains of Set rather than the assertJ implementation
        //        assertThat(parent.getChildren().contains(child1)).isTrue();
        //        assertThat(parent.getChildren().contains(child2)).isTrue();

        GeneratedIdParent readFromDb = dao
                .get(GeneratedIdParent.class, parent.getId());

        // is ok again, when entity is read from DB
        assertThat(readFromDb).isEqualToComparingFieldByField(parent);
        assertThat(readFromDb.getChildren()).containsExactlyInAnyOrder(child1, child2);
        assertThat(readFromDb.getChildren().contains(child1)).isTrue();
        assertThat(readFromDb.getChildren().contains(child2)).isTrue();
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

        runInTransaction(() -> dao.persist(parent));

        assertThat(parent.getChildren()).hasSize(2);
        assertThat(parent.getChildren()).containsExactlyInAnyOrder(child1, child2);
        // no problem as hashCode is NOT changed by persisting entity
        // directly use contains of Set rather than the assertJ implementation
        assertThat(parent.getChildren().contains(child1)).isTrue();
        assertThat(parent.getChildren().contains(child2)).isTrue();

        UuidParent readFromDb = dao
                .get(UuidParent.class, parent.getId());

        assertThat(readFromDb).isEqualToComparingFieldByField(parent);
        assertThat(readFromDb.getChildren()).containsExactlyInAnyOrder(child1, child2);
        assertThat(readFromDb.getChildren().contains(child1)).isTrue();
        assertThat(readFromDb.getChildren().contains(child2)).isTrue();
    }

    @Test
    void OneToMany_relation_should_be_lazy_by_default() {
        UuidParent parent = new UuidParent();
        UuidChild child1 = new UuidChild();
        child1.setName("child1");
        parent.addChild(child1);

        runInTransaction(() -> dao.persist(parent));

        UuidParent readFromDb = dao.get(UuidParent.class, parent.getId());

        //noinspection ResultOfMethodCallIgnored
        assertLazyInitialized(() -> readFromDb.getChildren().size());
    }

    @Test
    void should_support_lazy_OneToOne_relation() {
        ParkingSpace parkingSpace = new ParkingSpace();
        Employee employee = new Employee();
        employee.setParkingSpace(parkingSpace);

        runInTransaction(() -> dao.persist(parkingSpace, employee));

        Employee employeeFromDb = dao.get(Employee.class, employee.getId());
        //noinspection ResultOfMethodCallIgnored
        assertLazyInitialized(() -> employeeFromDb.getParkingSpace().getPosition());
    }

}