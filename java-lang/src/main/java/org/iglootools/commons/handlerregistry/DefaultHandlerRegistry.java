package org.iglootools.commons.handlerregistry;

import com.google.common.base.Preconditions;

/**
 *
 * @author sdalouche
 *
 * @param <K> The type that discriminates handlers (key)
 * @param <H> The type of the handlerregistry (usually a domain-specific interface
 */
public class DefaultHandlerRegistry<K,H> implements HandlerRegistry<K, H> {
    private HandlerLookupStrategy<K,H> handlerLookupStrategy;

    public DefaultHandlerRegistry(HandlerLookupStrategy<K,H> handlerLookupStrategy) {
        super();
        Preconditions.checkArgument(handlerLookupStrategy != null);
        this.handlerLookupStrategy = handlerLookupStrategy;
    }

    public H findHandlerFor(K key) {
        Preconditions.checkArgument(key != null);

        H found = handlerLookupStrategy.findHandlerFor(key);
        if(found == null)
        {
            throw new NoMatchingHandlerFoundException(key);
        }
        return found;
    }

}
