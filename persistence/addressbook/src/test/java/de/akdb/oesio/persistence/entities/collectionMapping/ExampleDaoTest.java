package de.akdb.oesio.persistence.entities.collectionMapping;

import de.akdb.oesio.persistence.entities.AbstractExampleDaoTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class ExampleDaoTest extends AbstractExampleDaoTest {

    ExampleDaoTest() {
        super("collection-mapping-pu");
    }

    @Test
    void should_persist_and_return_Employee_with_Collection_and_Embeddable() {
        Collection<EmbeddableAddress> addresses = Arrays.asList(
                EmbeddableAddress.createAddress("Hauptstraße", 42),
                EmbeddableAddress.createAddress("Dorfstraße", 7)
        );
        Collection<String> roles = Arrays.asList("Administrator", "Manager");
        Employee entity = Employee.createEmployee("Huber", roles, addresses);

        runInTransaction(() -> dao.persist(entity));

        Employee readFromDB = dao.get(Employee.class, entity.getId());

        assertThat(readFromDB).isEqualTo(entity);
    }

    @Test
    void should_persist_and_return_entity_with_list() {
        Department entity = new Department();
        entity.setName("Sales");

        runInTransaction(() -> dao.persist(entity));

        Department readFromDB = dao.get(Department.class, entity.getId());

        assertThat(readFromDB).isEqualTo(entity);
    }

}
