package de.akdb.oesio.persistence.entities;

import static java.lang.System.identityHashCode;
public abstract class BaseEntity<PK> {
    abstract PK getId();

    @Override
    public boolean equals(Object otherObj) {
        if (this == otherObj) {
            return true;
        }
        if (otherObj == null || this.getClass() != otherObj.getClass()) {
            return false;
        }
        BaseEntity<?> other = (BaseEntity<?>) otherObj;
        PK thisId = this.getId();
        Object otherId = other.getId();
        if (thisId != null && otherId != null) {
            return thisId.equals(otherId);
        }
        // fall back to object identity
        // both Ids null but not same object -> false
        // only one Id null, but not the other -> false
        return false;
    }

    @Override
    public int hashCode() {
        PK id = getId();
        if (id == null) {
            return identityHashCode(this);
        }
        return id.hashCode();
    }
}
