package de.akdb.oesio.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
public class UuidParent extends BaseUUIDEntity {
    @OneToMany(cascade = {ALL}, mappedBy = "parent")
    private Set<UuidChild> children = new HashSet<>();

    public Set<UuidChild> getChildren() {
        return children;
    }

    public void addChild(UuidChild child) {
        child.setParent(this);
        children.add(child);
    }
}
