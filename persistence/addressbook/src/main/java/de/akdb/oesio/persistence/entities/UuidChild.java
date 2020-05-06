package de.akdb.oesio.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class UuidChild extends BaseUUIDEntity {

    @Column
    private String name;

    @ManyToOne
    private UuidParent parent;

    public void setParent(UuidParent parent) {
        this.parent = parent;
    }

    public UuidParent getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
