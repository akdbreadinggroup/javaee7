package de.akdb.oesio.persistence.webshop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Stateless
public class OrderAktion {
    private static final Logger LOG = LoggerFactory.getLogger(OrderAktion.class);

    @PersistenceContext(unitName = "webshop-pu")
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public Order createOrder(String buyer, int itemId, int amount) {
        Order order = new Order();
        order.setAmount(amount);
        order.setBuyer(buyer);
        order.setItemId(itemId);
        em.persist(order);
        return order;
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    @Transactional(rollbackOn = Exception.class)
    public void exceptionBeiSchleichtemKaeufer(String buyer) throws Exception {
        if (buyer.contains("i")) {
            throw new Exception("Der zahlt nicht!");
        }
    }
}
