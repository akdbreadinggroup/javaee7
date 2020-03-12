package de.akdb.oesio.persistence.webshop;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Singleton;

//@startup
@Singleton
public class OrderCounter {
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
        System.out.println("PostConstruct");
    }

    @PreDestroy
    void shutdown() {
        System.out.println("PreDestroy");
    }
}
