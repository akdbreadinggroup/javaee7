package de.akdb.oesio.persistence.entities.collectionMapping;

import de.akdb.oesio.persistence.entities.BaseGeneratedIdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Department extends BaseGeneratedIdEntity {

    @Column
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
