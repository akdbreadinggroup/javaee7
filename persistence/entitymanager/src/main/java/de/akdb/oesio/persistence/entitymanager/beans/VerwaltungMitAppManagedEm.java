package de.akdb.oesio.persistence.entitymanager.beans;

import de.akdb.oesio.persistence.entitymanager.entities.Zaehler;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@Stateless
public class VerwaltungMitAppManagedEm {
    @PersistenceUnit(unitName = "jta-pu")
    private EntityManagerFactory emf;

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void erhoeheZaehler(int id, int differenz)
    {
        EntityManager em = emf.createEntityManager();
        Zaehler zaehler = em.find(Zaehler.class, id);
        zaehler.setAnzahl(zaehler.getAnzahl() + differenz);
    }
}
