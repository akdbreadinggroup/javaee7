package de.akdb.oesio.persistence.webshop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//@Dependent // geht auch, dann muss das Transaktionsattribut an der Methode anders spezifiziert werden als im EJB Fall und kann nicht weggelassen werden
@Stateless
//@TransactionManagement(TransactionManagementType.BEAN)// gibt bei EJBs Fehler
public class OrderService {
    private static final Logger LOG = LoggerFactory.getLogger(OrderService.class);

    @PersistenceContext(unitName = "webshop-pu")
    private EntityManager em;
    @Inject
    private OrderCounter orderCounter;
    @Inject
    private OrderAktion orderAktion;

    @Inject
    ProductService productService;

    @Resource
    private SessionContext context;

    // @Transactional(Transactional.TxType.REQUIRED)
    @TransactionAttribute(TransactionAttributeType.REQUIRED) // Default bei EJBs
    public String buy(String buyer, int itemId, int amount) throws Exception {

        if (buyer == null || amount <= 0 || !itemVorhanden(itemId)) {
            throw new Exception("Ohoh? Geht nicht!");
        }

        orderCounter.increment();
        orderAktion.createOrder(buyer, itemId, amount);
        orderAktion.exceptionBeiSchleichtemKaeufer(buyer);
        return "Kosten: " + amount * getPrice(itemId) + " erledigt von Orderservice: " + System.identityHashCode(this);
    }

    private boolean itemVorhanden(int itemId) {
        return productService.readAllProducts().stream().map(Product::getId).anyMatch(x -> x == itemId);
    }

    private double getPrice(int itemId) {
        Product product = em.find(Product.class, itemId);
        return product.getPrize();
    }

    @PostConstruct
    void startup() {
        LOG.warn("PostConstruct: " + System.identityHashCode(this));
    }

    @PreDestroy
    void shutdown() {
        LOG.warn("PreDestroy: " + System.identityHashCode(this));
    }

}
