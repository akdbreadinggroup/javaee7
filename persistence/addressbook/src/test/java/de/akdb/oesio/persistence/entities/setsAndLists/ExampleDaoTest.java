package de.akdb.oesio.persistence.entities.setsAndLists;

import de.akdb.oesio.persistence.entities.AbstractExampleDaoTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static de.akdb.oesio.persistence.entities.setsAndLists.EmbeddableAddress.createAddress;
import static de.akdb.oesio.persistence.entities.setsAndLists.Employee.createEmployee;
import static org.assertj.core.api.Assertions.assertThat;

class ExampleDaoTest extends AbstractExampleDaoTest {

    ExampleDaoTest() {
        super("sets-and-lists-pu");
    }

    @Test
    void should_persist_and_return_Employee_with_Collection_and_Embeddable() {
        Employee huberEntity = createHuber(null);

        runInTransaction(() -> dao.persist(huberEntity));

        Employee huberReadFromDB = dao.get(Employee.class, huberEntity.getId());

        assertThat(huberReadFromDB).isEqualTo(huberEntity);
    }

    @Test
    void should_persist_and_return_Department_with_ordered_Employees() {
        Department department = new Department();
        department.setName("Development");

        Employee huberEntity = createHuber(department);
        Employee schmittEntity = createSchmitt(department);
        Employee meierEntity = createMeier(department);
        List<Employee> employees = Arrays.asList(huberEntity, schmittEntity, meierEntity);

        runInTransaction(() -> dao.persist(department, huberEntity, meierEntity, schmittEntity));

        Department departmentReadFromDB = dao.get(Department.class, department.getId());

        assertThat(departmentReadFromDB.getEmployees()).containsExactly(huberEntity, schmittEntity, meierEntity);
    }


    private Employee createHuber(Department department) {
        Collection<EmbeddableAddress> addresses = Arrays.asList(
                createAddress("Hauptstraße", 42),
                createAddress("Dorfstraße", 7)
        );
        Collection<String> roles = Arrays.asList("Administrator", "Manager");
        return createEmployee("Huber", department, roles, addresses);
    }

    private Employee createMeier(Department department) {
        return createEmployee(
                "Meier",
                department,
                Collections.singleton("Entwickler"),
                Collections.singleton(createAddress("Marktplatz", 1)));
    }

    private Employee createSchmitt(Department department) {
        return createEmployee(
                "Schmitt",
                department,
                Collections.singleton("Entwickler"),
                Collections.singleton(createAddress("Landstraße", 1111)));
    }

}
