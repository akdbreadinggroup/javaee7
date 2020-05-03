package de.akdb.oesio.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
public class UuidChild extends BaseEntity<UUID> {

    @Id
    private UUID id;

    @Column
    private String name;

    @ManyToOne
    private UuidParent parent;

    protected UuidChild() {
        this.id = UUID.randomUUID();
    }

    @Override
    public UUID getId() {
        return id;
    }

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
