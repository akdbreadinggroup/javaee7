package de.akdb.oesio.persistence.webshop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;


//@startup
@Singleton
public class OrderCounter {
    private static final Logger LOG = LoggerFactory.getLogger(OrderCounter.class);

    int count;

    public void increment() {
        ++count;
    }

    public int getCount() {
        return count;
    }

    public void reset() {
        count = 0;
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
