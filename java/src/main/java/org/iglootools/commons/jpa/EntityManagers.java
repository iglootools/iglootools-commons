package org.iglootools.commons.jpa;

import com.google.common.base.Preconditions;
import org.hibernate.ejb.HibernateEntityManager;

import javax.persistence.EntityManager;

/**
 * @author Sami Dalouche (sami.dalouche@gmail.com)
 */
public class EntityManagers {
    /**
     * Helper method to check that the entity manager is hibernate, and to
     * return it
     *
     * @param entityManager
     * @return
     */
    public static HibernateEntityManager asHibernateEntityManager(EntityManager entityManager) {
        Preconditions.checkArgument(entityManager != null, "EntityManager cannot be null");
        Preconditions.checkArgument(HibernateEntityManager.class.isAssignableFrom(entityManager.getClass()), "Only Hibernate Entity Manager is supported");
        return (HibernateEntityManager) entityManager;
    }
}
