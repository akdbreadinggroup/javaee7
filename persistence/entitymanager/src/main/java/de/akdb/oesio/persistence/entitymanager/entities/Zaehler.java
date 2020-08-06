package de.akdb.oesio.persistence.entitymanager.entities;

import javax.persistence.*;

@Entity
public class Zaehler {
    public static final int ID_ZAEHLER_VON_HANS = 1;
    public static final int ID_ZAEHLER_VON_KARL = 2;
    @Id
    private int id;
    @Column
    private int anzahl;
    @Column
    @Basic(fetch = FetchType.LAZY)
    private String bezeichnung;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }
}
