package de.akdb.oesio.persistence.entities.maps;

import de.akdb.oesio.persistence.entities.BaseGeneratedIdEntity;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Department extends BaseGeneratedIdEntity {

    @Column
    private String name;

    @OneToMany(mappedBy = "department")
    @MapKey(name = "name")
    private Map<String,Employee> namesToEmployees = new HashMap<>();

    @ElementCollection
    @CollectionTable(name = "EmployeeRoles")
    @MapKeyJoinColumn(name = "employees_id")
    @Column(name = "Rolle")
    private Map<Employee, String> employeeToRole = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addEmployee(Employee employee) {
        namesToEmployees.put(employee.getName(), employee);
        employeeToRole.put(employee, employee.getRole());
    }

    public Map<Employee, String> getEmployeeToRole() {
        return employeeToRole;
    }

    public Map<String, Employee> getNamesToEmployees() {
        return namesToEmployees;
    }
}
