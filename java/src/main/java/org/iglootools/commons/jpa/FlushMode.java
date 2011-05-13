package org.iglootools.commons.jpa;

/**
 * Used to abstract the Flush Mode of the underlying persistence framework.
 *
 * @see javax.persistence.FlushMode
 * @see org.hibernate.FlushMode
 *
 * @author Sami Dalouche (sami.dalouche@gmail.com)
 *
 */

public enum FlushMode {

    /**
     * Let the persistence framework handle the flushes
     */
    AUTO,
    /**
     * Only flush at commit time
     */
    COMMIT
}
