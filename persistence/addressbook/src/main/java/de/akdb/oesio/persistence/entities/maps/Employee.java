package de.akdb.oesio.persistence.entities.maps;

import de.akdb.oesio.persistence.entities.BaseGeneratedIdEntity;

import javax.persistence.*;
import java.util.*;

@Entity
public class Employee extends BaseGeneratedIdEntity {

    @Column
    private String name;

    @Column
    private String role;

    // Besser: String-Value eines Enums als Key verwenden mit @MapKeyEnumerated
    @ElementCollection
    @CollectionTable(name = "Adressen")
    @MapKeyColumn(name = "Typ")
    private Map<String,EmbeddableAddress> addresses = new HashMap<>();

    @ManyToOne
    private Department department;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {

        return name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void addAdress(String type, EmbeddableAddress adress) {
        addresses.put(type, adress);
    }

    public Map<String, EmbeddableAddress> getAddresses() {
        return addresses;
    }


    public static Employee createEmployee(String name, Department department, String role, Map<String, EmbeddableAddress> addresses) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setDepartment(department);
        employee.setRole(role);
        if (department != null){
            department.addEmployee(employee);
        }
        employee.addresses.putAll(addresses);
        return employee;
    }
}
