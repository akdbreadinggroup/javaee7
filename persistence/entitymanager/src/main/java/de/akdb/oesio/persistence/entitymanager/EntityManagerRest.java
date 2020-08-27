package de.akdb.oesio.persistence.entitymanager;

import de.akdb.oesio.persistence.entitymanager.beans.StatefullService;
import de.akdb.oesio.persistence.entitymanager.beans.StatelessService;
import de.akdb.oesio.persistence.entitymanager.beans.VerwaltungMitCmEm;
import de.akdb.oesio.persistence.entitymanager.beans.VerwaltungResourceLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static de.akdb.oesio.persistence.entitymanager.entities.Zaehler.ID_ZAEHLER_VON_HANS;

@Path("/")
public class EntityManagerRest {
    private static final Logger LOG = LoggerFactory.getLogger(EntityManagerRest.class);
    @Context
    private UriInfo uriInfo;
    @Context
    private Request request;
    @Inject
    private StatelessService statelessService;
    @Inject
    private StatefullService statefullService;
    @Inject
    private VerwaltungResourceLocal verwaltungResourceLocal;

    public EntityManagerRest() {
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/beispiele")
    public String getAlleBeispiele() {
        return webseiteFuer("");
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/test")
    public String test() {
        return webseiteMitExceptionAnzeigeUm(() ->
        {

            return ladeZaehler();
        });
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/resourcelocal")
    public String resourcelocal() {
        return webseiteMitExceptionAnzeigeUm(() ->
        {
            verwaltungResourceLocal.erhoeheZaehler(ID_ZAEHLER_VON_HANS, 1);
            return ladeZaehler();
        });
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/reset")
    public String reset() {
        return webseiteMitExceptionAnzeigeUm(() ->
        {
            statelessService.resetZaehler();
            return ladeZaehler();
        });
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/erhoeheZaehlerZweiMal")
    public String erhoeheZaehlerZweiMal() {
        return webseiteMitExceptionAnzeigeUm(() ->
        {
            statelessService.erhoeheZaehlerZweiMal();
            return ladeZaehler();
        });
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/erhoeheZaehlerUndErhoeheZaehlerInNewTx")
    public String erhoeheZaehlerUndErhoeheZaehlerInNewTx() {
        return webseiteMitExceptionAnzeigeUm(() ->
        {
            statelessService.erhoeheZaehlerUndErhoeheZaehlerInNewTx();
            return ladeZaehler();
        });
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/unsynchronisierteEmsMuessenExpizitGejointWerden")
    public String unsynchronisierteEmsMuessenExpizitGejointWerden() {
        return webseiteMitExceptionAnzeigeUm(() ->
        {
            statelessService.unsynchronisierteEmsMuessenExpizitGejointWerden();
            return ladeZaehler();
        });
    }


    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/propagation")
    public String propagation() {
        return webseiteMitExceptionAnzeigeUm(() ->
        {
            statelessService.reihenfolgeDerOperationenWirdDurchPCPropagationGarantiert();
            return ladeZaehler();
        });
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/keinePropagationBeiAppManagedEms")
    public String keinePropagationBeiAppManagedEms() {
        return webseiteMitExceptionAnzeigeUm(() ->
        {
            statelessService.appManagedEmsPropagierendenPersistenceContextNicht();
            return ladeZaehler();
        });
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/extendedscope")
    public String extendedscope() {
        return webseiteMitExceptionAnzeigeUm(() ->
        {
            statefullService.erhoeheZaehlerInStatefullServiceMitTransaktionUm8();
            statelessService.erhoeheZaehlerZweiMal();
            statefullService.erhoeheZaehlerUeberMemberUm4();
            statefullService.erhoeheZaehlerOhneTxUm1();
            statefullService.erhoeheZaehlerInStatefullServiceMitTransaktionUm16();
            return ladeZaehler();
        });
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/pccollision")
    public String pccollision() {
        return webseiteMitExceptionAnzeigeUm(() ->
        {
            statelessService.erzeuge_PC_collision();
            return "Hier sollte eigentlich eine Exception kommen.";
        });
    }

    private String ladeZaehler() {
        return "Hans: " + statelessService.ladeZaehlerstand();
    }

    private String webseiteMitExceptionAnzeigeUm(Callable<String> funktion) {
        try {
            String result = webseiteFuer(funktion.call());
            statefullService.finish();
            return result;
        } catch (Exception e) {
            LOG.error("Exception", e);
            statefullService.cancel();
            return webseiteFuer("<h2>Exception</h2><h3>" + e.getMessage() + "</h3>" + Arrays.stream(e.getStackTrace()).map(x -> x + "<br>").collect(Collectors.joining()));
        }
    }

    private String webseiteFuer(String inhalt) {
        return "<html lang=\"en\"><body>" + getBeispielliste() + "<h1>Ergebnis</h1>" + inhalt + "</body></html>";
    }

    private String getBeispielliste() {
        return "<h1>Beispiele Kapitel 6</h1><ul>"
                + "<li><a href=\"http://127.0.0.1:8080/entitymanager/test\">Test</a></li>"
                + "<li><a href=\"http://127.0.0.1:8080/entitymanager/reset\">Zaehler zuruecksetzen</a></li>"
                + "<li><a href=\"http://127.0.0.1:8080/entitymanager/erhoeheZaehlerZweiMal\">[JTA, CM-EM, transaction scoped, Bsp 1]</a> Stateless Service ruft in einer Transaktion zweimal Methode in Stateless Auskunft auf, die den Zaehler hoch setzt.</li>"
                + "<li><a href=\"http://127.0.0.1:8080/entitymanager/erhoeheZaehlerUndErhoeheZaehlerInNewTx\">[JTA, CM-EM, transaction scoped, Bsp 2]</a> Stateless Service ruft in einer Transaktion zweimal Methode in Stateless Auskunft auf, die den Zaehler hoch setzt, beim zweiten mal macht die Auskunft aber neue Transaktion auf.</li>"
                + "<li><a href=\"http://127.0.0.1:8080/entitymanager/propagation\">[JTA, CM-EM, Propagation]</a> Persistence context wird ueber Bean Grenzen transportiert. Anders als bei synchronisierten App Managed EMs ist die Reihenfolge beim flushen so garantiert.</li>"
                + "<li><a href=\"http://127.0.0.1:8080/entitymanager/keinePropagationBeiAppManagedEms\">[JTA, App-EM, Keine Propagation]</a> Persistence context wird nicht ueber Bean Grenzen transportiert.</li>"
                + "<li><a href=\"http://127.0.0.1:8080/entitymanager/unsynchronisierteEmsMuessenExpizitGejointWerden\">[JTA, CM-EM, Unsynchronized EM]</a> Unsynchronisierte EMs muessen explizit gejoint werden.</li>"
                + "<li><a href=\"http://127.0.0.1:8080/entitymanager/extendedscope\">[JTA, CM-EM, extended scoped]</a> Statefull Bean mit extended scoped EM haelt einen Persistence Context auch uebertransaktionen hinweg, oder wenn sich die DB geaendert hat. Wenn der extedned scope entfernt wird ist das Ergebnis anders.</li>"
                + "<li><a href=\"http://127.0.0.1:8080/entitymanager/pccollision\">[JTA, CM-EM, PC collision]</a> Stateless Bean ruft in Transaktion Statefull Bean auf.<b>Achtung! Irgendwas stimmt noch nicht mit den StatefullService. Nach dieser Methode funktioniert er nicht mehr bis man den JBoss neu startet.</b> </li>"
                + "<li><a href=\"http://127.0.0.1:8080/entitymanager/resourcelocal\">[Resource local]</a> Kann man auch machen. </li>"
                + "</ul>";
    }
}
