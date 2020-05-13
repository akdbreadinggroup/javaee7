package de.akdb.oesio.persistence.entities.maps;

import de.akdb.oesio.persistence.entities.AbstractExampleDaoTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static de.akdb.oesio.persistence.entities.maps.EmbeddableAddress.createAddress;
import static de.akdb.oesio.persistence.entities.maps.Employee.createEmployee;
import static org.assertj.core.api.Assertions.assertThat;

class ExampleDaoTest extends AbstractExampleDaoTest {

    ExampleDaoTest() {
        super("maps-pu");
    }

    /* Keying by Basic Type */
    @Test
    void should_persist_and_return_Employee_with_Map_of_addresses() {
        Employee huberEntity = createHuber(null);

        runInTransaction(() -> dao.persist(huberEntity));

        Employee huberReadFromDB = dao.get(Employee.class, huberEntity.getId());

        assertThat(huberReadFromDB).isEqualTo(huberEntity);
    }


    @Test
    void should_persist_and_return_Department_with_Map_of_Employees() {
        Department department = new Department();
        department.setName("Development");

        Employee huberEntity = createHuber(department);
        Employee schmittEntity = createSchmitt(department);
        Employee meierEntity = createMeier(department);

        runInTransaction(() -> dao.persist(department, huberEntity, schmittEntity, meierEntity));

        Department readFromDB = dao.get(Department.class, department.getId());

        /* Keyed by Entity Attribute */
        assertThat(readFromDB.getNamesToEmployees()).containsAllEntriesOf(department.getNamesToEmployees());
        /* Keyed by Entity */
        assertThat(readFromDB.getEmployeeToRole()).containsAllEntriesOf(department.getEmployeeToRole());
    }



    private Employee createHuber(Department department) {
        Map<String,EmbeddableAddress> addresses = new HashMap<>();
        addresses.put("HOME", createAddress("Hauptstraße", 42));
        addresses.put("WORK", createAddress("Dorfstraße", 7));

        return createEmployee("Huber", department, "Manager", addresses);
    }

    private Employee createMeier(Department department) {
        Map<String,EmbeddableAddress> addresses = new HashMap<>();
        addresses.put("HOME", createAddress("Marktplatz", 1));

        return createEmployee( "Meier", department, "Entwickler", addresses);
    }

    private Employee createSchmitt(Department department) {
        Map<String,EmbeddableAddress> addresses = new HashMap<>();
        addresses.put("POST", createAddress("Landstraße", 1111));

        return createEmployee("Schmitt", department, "Entwickler", addresses);
    }

}
