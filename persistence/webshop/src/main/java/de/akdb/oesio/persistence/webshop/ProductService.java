package de.akdb.oesio.persistence.webshop;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class ProductService {
    @PersistenceContext(unitName = "webshop-pu")
    EntityManager em;

    public List<Product> readAllProducts() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Product> query = criteriaBuilder.createQuery(Product.class);
        Root<Product> rootEntry = query.from(Product.class);
        CriteriaQuery<Product> all = query.select(rootEntry);

        TypedQuery<Product> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

    public List<Product> findProductById(int id) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Product> query = criteriaBuilder.createQuery(Product.class);
        Root<Product> rootEntry = query.from(Product.class);

        query.select(rootEntry);
        query.where(criteriaBuilder.equal(rootEntry.get("id"), String.valueOf(id)));

        TypedQuery<Product> allQuery = em.createQuery(query);
        return allQuery.getResultList();
    }

    @PostConstruct
    void startup() {
        System.out.println("PostConstruct");
    }

    @PreDestroy
    void shutdown() {
        System.out.println("PreDestroy");
    }
}
