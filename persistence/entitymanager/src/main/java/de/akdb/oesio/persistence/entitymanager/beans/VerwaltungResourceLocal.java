package de.akdb.oesio.persistence.entitymanager.beans;

import de.akdb.oesio.persistence.entitymanager.entities.Zaehler;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@Dependent
public class VerwaltungResourceLocal {
    @PersistenceUnit(unitName = "resource-lokal-pu")
    private EntityManagerFactory emf;

    public void erhoeheZaehler(int id, int differenz) {
        //EntityManagerFactory emf = Persistence.createEntityManagerFactory("resource-lokal-pu");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();//em.getTransaction() geht nicht bei JTA
        Zaehler zaehler = em.find(Zaehler.class, id);
        zaehler.setAnzahl(zaehler.getAnzahl() + differenz);
        em.getTransaction().commit();
    }
}
