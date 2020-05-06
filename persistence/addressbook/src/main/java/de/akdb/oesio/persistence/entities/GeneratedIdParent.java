package de.akdb.oesio.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
public class GeneratedIdParent extends BaseGeneratedIdEntity {
    @OneToMany(cascade = {ALL}, mappedBy = "parent")
    private final Set<GeneratedIdChild> children = new HashSet<>();

    public Set<GeneratedIdChild> getChildren() {
        return children;
    }

    public void addChild(GeneratedIdChild child) {
        child.setParent(this);
        children.add(child);
    }
}
