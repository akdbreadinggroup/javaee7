package de.akdb.oesio.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static javax.persistence.CascadeType.ALL;

@Entity
public class UuidParent extends BaseEntity<UUID> {

    @Id
    private UUID id;

    @OneToMany(cascade = {ALL}, mappedBy = "parent")
    private Set<UuidChild> children = new HashSet<>();

    protected UuidParent() {
        this.id = UUID.randomUUID();
    }

    @Override
    public UUID getId() {
        return id;
    }

    public Set<UuidChild> getChildren() {
        return children;
    }

    public void addChild(UuidChild child) {
        child.setParent(this);
        children.add(child);
    }
}
