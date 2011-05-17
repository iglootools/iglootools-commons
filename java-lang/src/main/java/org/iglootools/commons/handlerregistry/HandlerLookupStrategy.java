package org.iglootools.commons.handlerregistry;


public interface HandlerLookupStrategy<K,H> {
    H findHandlerFor(K key);
}