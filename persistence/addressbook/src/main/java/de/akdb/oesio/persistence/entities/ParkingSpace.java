package de.akdb.oesio.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ParkingSpace extends BaseUUIDEntity {
    @Column
    private String position;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
