package de.akdb.oesio.persistence.entities.collectionMapping;

import de.akdb.oesio.persistence.entities.BaseGeneratedIdEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Department extends BaseGeneratedIdEntity {

    @Column
    private String name;

    @OneToMany(mappedBy = "department")
    @OrderBy("name DESC")
    //@OrderColumn(name="Reihenfolge")
    private List<Employee> employees = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}
