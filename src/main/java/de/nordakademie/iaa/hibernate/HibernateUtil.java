package de.nordakademie.iaa.hibernate;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Hibernate util class.
 */
public class HibernateUtil {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory("NakDB");

    /**
     * Private constructor to prevent instantiation.
     */
    private HibernateUtil() {
        // Nothing here
    }

    /**
     * Returns the entity manager factory.
     * @return the entity manager factory.
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        return ENTITY_MANAGER_FACTORY;



    }

}
