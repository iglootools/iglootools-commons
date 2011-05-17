package org.iglootools.commons.handlerregistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public final class CacheLookupStrategy<K,H> extends AbstractLookupStrategy<K,H> {
    private Map<K, H> cachedHandlers = new ConcurrentHashMap<K, H>();

    public CacheLookupStrategy(HandlerLookupStrategy<K,H> other)
    {
        super(other);
    }

    @Override
    public H doLookupHandler(K key) {
        return cachedHandlers.get(key);
    }

    /**
     * FIXME: theoretically, we should provide a Null object so that we also cache
     * the null result..
     */
    @Override
    protected void doAfterOtherLookup(K key, H foundHandler)
    {
        if(foundHandler != null)
        {
            cachedHandlers.put(key, foundHandler);
        }

    }
}