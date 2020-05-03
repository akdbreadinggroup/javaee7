package de.akdb.oesio.persistence.entities;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class GeneratedIdChild extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToOne
    private GeneratedIdParent parent;

    @Override
    public Long getId() {
        return id;
    }

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
