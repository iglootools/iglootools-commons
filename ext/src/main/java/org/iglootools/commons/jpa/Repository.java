package org.iglootools.commons.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;

import org.hibernate.Session;


/**
 * Implementation of the DDD Repository Design Pattern.
 *
 * <ul>
 * <li>Allows to easily replace the Persistence Engine and Technology</li>
 * <li>Since Queries are expressed as Strings and thus are not refactorable, it
 * is easier to change them in one place in case of a refactoring</li>
 * <li>Easier global optimization of the queries</li>
 * <li></li>
 * </ul>
 *
 * @author Sami Dalouche (sami.dalouche@gmail.com)
 *
 */
public interface Repository<T> {

    /**
     * Returns all instances
     *
     * @return
     */
    public List<T> findAll();

    /**
     * Flush all Memory Objects to the database, and clear the L1 cache, of the
     * current thread-bound JPA Entity Manager, or Hibernate Session. (or
     * equivalent for other persistence APIs).
     *
     * FIXME: this should not be here. Maybe into some kind of AOP ? FIXME: is
     * it better to have that in a static class/method ?
     */
    public void flushAndClear();

    /**
     * Saves the passed object, and returns an attached entity. It is very very
     * very very very important to use the returned entity, because of the way
     * the underlying mechanism possibly works. For instance, when using Db4o,
     * this is completly useless, but when using JPA and the merge method of the
     * EntityManager, things are going to be buggy (Duplicate Key exceptions) if
     * you don't use the returned object. Please consult EJB3 (or the concrete
     * persistence framework doc) Spec for more information about the way the
     * merge method works.
     *
     * @param o
     * @return
     */
    public T save(final T o);

    /**
     * Sets the flush mode (i.e. when objects are flushed to the database) of
     * the current thread-bound session. By default, it is equivalent (in the
     * case of JPA persistence) to {@link FlushModeType#AUTO}, which lets the
     * persistence framework handle that issue. However, for performance
     * reasons, it might be necessary to set it to {@link FlushModeType#COMMIT}.
     *
     * Warning : this sets the default flush mode of the session (either
     * hibernate {@link Session}, JPA {@link EntityManager}, or similar) that
     * is currently bound to the current thread. This means that it has
     * absolutely no effect if no transaction is currently opened.
     *
     * FIXME: this should not be here. Maybe into some kind of AOP ? FIXME: is
     * it better to have that in a static class/method ?
     *
     * @param flushMode
     */
    public void setFlushMode(FlushMode flushMode);
}
