package de.akdb.oesio.persistence.entitymanager.beans;

import de.akdb.oesio.persistence.entitymanager.entities.Zaehler;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.SynchronizationType;

@Stateless
public class VerwaltungMitUnsynchronizedEm {
    @PersistenceContext(unitName = "jta-pu", synchronization = SynchronizationType.UNSYNCHRONIZED)
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void erhoeheZaehler(int id, int differenz) {
        Zaehler zaehler = em.find(Zaehler.class, id);
        zaehler.setAnzahl(zaehler.getAnzahl() + differenz);
        //em.joinTransaction();
    }
}
