package de.akdb.oesio.persistence.entitymanager.beans;

import de.akdb.oesio.persistence.entitymanager.entities.Zaehler;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class VerwaltungMitCmEm {
    @PersistenceContext(unitName = "jta-pu")
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Zaehler ladeZaehler(int id) {
        return em.find(Zaehler.class, id);
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void setzeZaehler(int id, int wert) {
        Zaehler zaehler = em.find(Zaehler.class, id);
        zaehler.setAnzahl(wert);
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void erhoeheZaehler(int id, int differenz) {
        Zaehler zaehler = em.find(Zaehler.class, id);
        zaehler.setAnzahl(zaehler.getAnzahl() + differenz);
    }

    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void erhoeheZaehlerOhneTransaktion(int id, int differenz) {
        Zaehler zaehler = em.find(Zaehler.class, id);
        zaehler.setAnzahl(zaehler.getAnzahl() + differenz);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void erhoeheZaehlerInNewTx(int id, int differenz) {
        erhoeheZaehler(id, differenz);
    }
}
