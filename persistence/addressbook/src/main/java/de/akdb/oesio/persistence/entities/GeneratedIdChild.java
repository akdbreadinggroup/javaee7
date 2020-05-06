package de.akdb.oesio.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class GeneratedIdChild extends BaseGeneratedIdEntity {

    @Column
    private String name;

    @ManyToOne
    private GeneratedIdParent parent;

    public void setParent(GeneratedIdParent parent) {
        this.parent = parent;
    }

    public GeneratedIdParent getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
