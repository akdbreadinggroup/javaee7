package de.akdb.oesio.persistence.entitymanager.beans;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import static de.akdb.oesio.persistence.entitymanager.entities.Zaehler.ID_ZAEHLER_VON_HANS;

@Stateless
public class StatelessService {
    @Inject
    private VerwaltungMitCmEm verwaltungMitCmEm;
    @Inject
    private StatefullService statefullService;
    @Inject
    private VerwaltungMitAppManagedEm verwaltungMitAppManagedEm;
    @Inject
    private VerwaltungMitUnsynchronizedEm verwaltungMitUnsynchronizedEm;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void erhoeheZaehlerZweiMal() {
        verwaltungMitCmEm.erhoeheZaehler(ID_ZAEHLER_VON_HANS, 1);
        verwaltungMitCmEm.erhoeheZaehler(ID_ZAEHLER_VON_HANS, 1);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void erhoeheZaehlerUndErhoeheZaehlerInNewTx() {
        verwaltungMitCmEm.erhoeheZaehler(ID_ZAEHLER_VON_HANS, 1);
        verwaltungMitCmEm.erhoeheZaehlerInNewTx(ID_ZAEHLER_VON_HANS, 1);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void reihenfolgeDerOperationenWirdDurchPCPropagationGarantiert() {
        verwaltungMitCmEm.setzeZaehler(ID_ZAEHLER_VON_HANS, 3);
        verwaltungMitCmEm.setzeZaehler(ID_ZAEHLER_VON_HANS, 5);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void appManagedEmsPropagierendenPersistenceContextNicht() {
        verwaltungMitAppManagedEm.erhoeheZaehler(ID_ZAEHLER_VON_HANS, 1);
        verwaltungMitAppManagedEm.erhoeheZaehler(ID_ZAEHLER_VON_HANS, 2);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void unsynchronisierteEmsMuessenExpizitGejointWerden() {
        verwaltungMitUnsynchronizedEm.erhoeheZaehler(ID_ZAEHLER_VON_HANS, 1);
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public int ladeZaehlerstand() {
        return verwaltungMitCmEm.ladeZaehler(ID_ZAEHLER_VON_HANS).getAnzahl();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void resetZaehler() {
        verwaltungMitCmEm.setzeZaehler(ID_ZAEHLER_VON_HANS, 0);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void erzeuge_PC_collision() {
        resetZaehler();
        statefullService.erhoeheZaehlerInStatefullServiceMitTransaktionUm8();
    }
}
