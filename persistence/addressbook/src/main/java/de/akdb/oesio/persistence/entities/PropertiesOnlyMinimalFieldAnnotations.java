package de.akdb.oesio.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class PropertiesOnlyMinimalFieldAnnotations extends BaseUUIDEntity {
    @Column
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
