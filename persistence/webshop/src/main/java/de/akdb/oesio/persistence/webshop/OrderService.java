package de.akdb.oesio.persistence.webshop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class OrderService {
    private static final Logger LOG = LoggerFactory.getLogger(OrderService.class);

    @PersistenceContext(unitName = "webshop-pu")
    private EntityManager em;
    @Inject
    private OrderCounter orderCounter;

    @Inject
    ProductService productService;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public double buy(String buyer, int itemId, int amount) throws Exception {

        if (buyer == null || amount <= 0 || !itemVorhanden(itemId)) {
            throw new Exception("Ohoh? Geht nicht!");
        }

        orderCounter.increment();
        createOrder(buyer, itemId, amount);
        return amount * getPrice(itemId);
    }

    private boolean itemVorhanden(int itemId) {
        return productService.readAllProducts().stream().map(Product::getId).anyMatch(x -> x == itemId);
    }

    private double getPrice(int itemId) {
        Product product = em.find(Product.class, itemId);
        return product.getPrize();
    }

    private Order createOrder(String buyer, int itemId, int amount) {
        Order order = new Order();
        order.setAmount(amount);
        order.setBuyer(buyer);
        order.setItemId(itemId);
        em.persist(order);
        return order;
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
