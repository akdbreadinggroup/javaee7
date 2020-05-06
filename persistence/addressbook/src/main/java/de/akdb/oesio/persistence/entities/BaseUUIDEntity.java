package de.akdb.oesio.persistence.entities;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseUUIDEntity extends BaseEntity<UUID> {
    @Id
    private UUID id;

    protected BaseUUIDEntity() {
        this.id = UUID.randomUUID();
    }

    @Override
    public UUID getId() {
        return id;
    }
}
