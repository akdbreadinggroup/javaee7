package de.akdb.oesio.persistence.addressbook;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Path("/contact")
public class ContactRest {

    private static final Function<Contact, String> contactToString = contact -> contact.getId() + " " + contact.getFirstName() + " " + contact.getLastName() + " " + contact.getEmail() + " " + contact.getMobilePhone() + " " + contact.getHomePhone() + " " + contact.getBirthday();

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("addressbook-pu");
    private static final EntityManager em = emf.createEntityManager();

    @Context
    private UriInfo context;

    public ContactRest() {
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/all")
    public String getAllContacts() {
        return "<html lang=\"en\"><body>" + findAllContacts() + "</body></html>";
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("{id}")
    public String getContactById(@PathParam("id") int id) {
        return "<html lang=\"en\"><body>" + findContact(id) + "</body></html>";
    }

    private String findContact(int id) {
        List<Contact> contact = findContactById(id);

        if (contact.isEmpty()) {
            return "<h1>" + "keine Eintraege fuer ID=" + id +"</h1>";
        } else {
            return formatContacts(contact);
        }
    }

    private static String findAllContacts() {
        List<Contact> contacts = readAllContacts();

        return formatContacts(contacts);
    }

    private static String formatContacts(List<Contact> contacts) {
        return contacts.stream()
                .map(contactToString)
                .map(s -> "<h1>" + s + "</h1>")
                .collect(Collectors.joining());
    }

    private static List<Contact> readAllContacts() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Contact> query = criteriaBuilder.createQuery(Contact.class);
        Root<Contact> rootEntry = query.from(Contact.class);
        CriteriaQuery<Contact> all = query.select(rootEntry);

        TypedQuery<Contact> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

    private static List<Contact> findContactById(int id) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Contact> query = criteriaBuilder.createQuery(Contact.class);
        Root<Contact> rootEntry = query.from(Contact.class);

        query.select(rootEntry);
        query.where(criteriaBuilder.equal(rootEntry.get("id"), String.valueOf(id)));

        TypedQuery<Contact> allQuery = em.createQuery(query);
        return allQuery.getResultList();
    }
}