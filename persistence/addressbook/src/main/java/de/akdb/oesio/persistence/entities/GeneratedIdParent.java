package de.akdb.oesio.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class GeneratedIdParent extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToMany(cascade = {ALL}, mappedBy = "parent")
    private Set<GeneratedIdChild> children = new HashSet<>();

    @Override
    public Long getId() {
        return id;
    }

    public Set<GeneratedIdChild> getChildren() {
        return children;
    }

    public void addChild(GeneratedIdChild child) {
        child.setParent(this);
        children.add(child);
    }
}
