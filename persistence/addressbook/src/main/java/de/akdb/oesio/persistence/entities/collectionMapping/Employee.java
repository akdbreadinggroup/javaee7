package de.akdb.oesio.persistence.entities.collectionMapping;

import de.akdb.oesio.persistence.entities.BaseGeneratedIdEntity;

import javax.persistence.*;
import java.util.*;

@Entity
public class Employee extends BaseGeneratedIdEntity {

    @Column
    private String name;

    @ElementCollection
    private Set<String> roles = new HashSet<>();

//    @CollectionTable(name="Adressen", joinColumns = @JoinColumn(name="emp_id"))
//    @AttributeOverrides({
//            @AttributeOverride(name = "street", column = @Column(name = "Strasse")),
//            @AttributeOverride(name = "number", column = @Column(name = "Hsnr"))
//    })
    @ElementCollection
    private Collection<EmbeddableAddress> addresses = new LinkedList<>();

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {

        return name;
    }

    public void addRole(String role) {
        roles.add(role);
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void addAdress(EmbeddableAddress adress) {
        addresses.add(adress);
    }

    public Collection<EmbeddableAddress> getAddresses() {
        return addresses;
    }

    public static Employee createEmployee(String name, Collection<String> roles, Collection<EmbeddableAddress> addresses) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.roles.addAll(roles);
        employee.addresses.addAll(addresses);
        return employee;
    }
}
