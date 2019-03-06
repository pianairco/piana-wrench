package ir.piana.dev.wrench.core.module;

import javax.persistence.EntityManagerFactory;

public interface QPEntityManagerFactoryProvider {
    EntityManagerFactory getEntityManagerFactory();
}
