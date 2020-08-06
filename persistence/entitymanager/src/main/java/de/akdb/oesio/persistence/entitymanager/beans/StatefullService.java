package de.akdb.oesio.persistence.entitymanager.beans;

import de.akdb.oesio.persistence.entitymanager.entities.Zaehler;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import static de.akdb.oesio.persistence.entitymanager.entities.Zaehler.ID_ZAEHLER_VON_HANS;

@Stateful
public class StatefullService {
    @PersistenceContext(unitName = "jta-pu",
            type = PersistenceContextType.EXTENDED
            //, synchronization = SynchronizationType.UNSYNCHRONIZED
    )
    private EntityManager em;
    @Inject
    private VerwaltungMitCmEm verwaltungMitCmEm;

    private Zaehler zaehler;

    @PostConstruct
    public void init() {// verstehe ich nicht
        zaehler = em.find(Zaehler.class, ID_ZAEHLER_VON_HANS);
    }

    @Remove
    public void finish() {
        // em.joinTransaction();
    }

    @Remove
    public void cancel() {
        em.clear();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void erhoeheZaehlerInStatefullServiceMitTransaktionUm8() {
        verwaltungMitCmEm.erhoeheZaehler(ID_ZAEHLER_VON_HANS, 8);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void erhoeheZaehlerInStatefullServiceMitTransaktionUm16() {
        verwaltungMitCmEm.erhoeheZaehler(ID_ZAEHLER_VON_HANS, 16);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void erhoeheZaehlerOhneTxUm1() {
        verwaltungMitCmEm.erhoeheZaehlerOhneTransaktion(ID_ZAEHLER_VON_HANS, 1);
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void erhoeheZaehlerUeberMemberUm4() {
        zaehler.setAnzahl(zaehler.getAnzahl() + 4);
    }
}
