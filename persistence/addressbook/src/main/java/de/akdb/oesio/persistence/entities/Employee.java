package de.akdb.oesio.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import static javax.persistence.FetchType.LAZY;

@Entity
public class Employee extends BaseUUIDEntity {
    @OneToOne(fetch = LAZY)
    private ParkingSpace parkingSpace;

    public ParkingSpace getParkingSpace() {
        return parkingSpace;
    }

    public void setParkingSpace(ParkingSpace parkingSpace) {
        //        parkingSpace.setEmployee(this);
        this.parkingSpace = parkingSpace;
    }
}
