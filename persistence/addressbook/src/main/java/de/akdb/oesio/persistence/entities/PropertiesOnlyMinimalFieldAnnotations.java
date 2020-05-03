package de.akdb.oesio.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class PropertiesOnlyMinimalFieldAnnotations extends BaseEntity<UUID> {
    @Id
    private final UUID id;

    @Column
    private String name;

    protected PropertiesOnlyMinimalFieldAnnotations() {
        this.id = UUID.randomUUID();
    }

    @Override
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
