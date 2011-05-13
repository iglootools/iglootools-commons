package org.iglootools.commons.jpa;

import org.junit.Test;
import org.mockito.Mockito;

import javax.persistence.EntityManager;

/**
 * @author Sami Dalouche (sami.dalouche@gmail.com)
 */
public class EntityManagersTest {
    @Test(expected=IllegalArgumentException.class)
    public void shouldNotCastNullValue() {
        EntityManagers.asHibernateEntityManager(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldNotCastIfNotAssignableToHibernateEntityManager() {
        EntityManager entityManager = Mockito.mock(EntityManager.class);
        EntityManagers.asHibernateEntityManager(entityManager);
    }
}