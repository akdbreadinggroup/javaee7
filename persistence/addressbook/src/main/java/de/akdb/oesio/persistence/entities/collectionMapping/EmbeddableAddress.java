package de.akdb.oesio.persistence.entities.collectionMapping;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EmbeddableAddress {

    @Column(name="road")
    private String street;

    //@Column(name="number")
    private int number;

    public String getStreet() {
        return street;
    }

    public int getNumber() {
        return number;
    }

    public void setStreet(String street) {

        this.street = street;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public static EmbeddableAddress createAddress(String street, int number) {
        EmbeddableAddress address = new EmbeddableAddress();
        address.setStreet(street);
        address.setNumber(number);
        return address;
    }
}
