package de.akdb.oesio.persistence.webshop;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@Stateful
public class ShoppingCart {
    @PersistenceUnit(unitName="webshop-pu")
    private EntityManagerFactory emf;
    private EntityManager em;

    @Resource
    private SessionContext context;

    @PostConstruct
    void startup() {
        System.out.println("PostConstruct");
        em = emf.createEntityManager();
    }

    @PrePassivate
    void passivate() {
        System.out.println("PrePassivate");
    }


    @PostActivate
    void activate() {
        System.out.println("PostActivate");
    }

    @PreDestroy
    void shutdown() {
        System.out.println("PreDestroy");
    }

    @Remove
    void cancel() {

    }

    @Remove
    void doOrder() {

    }
}
