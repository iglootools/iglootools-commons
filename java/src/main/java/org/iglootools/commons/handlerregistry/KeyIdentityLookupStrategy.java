package org.iglootools.commons.handlerregistry;

import java.util.Map;

import com.google.common.collect.ImmutableMap;


public final class KeyIdentityLookupStrategy<K,H> extends AbstractLookupStrategy<K, H> {
    public H findHandlerFor(K key, Map<K, H> handlers, Map<K, H> cachedHandlers) {
        return handlers.get(key);
    }
    private ImmutableMap<K, H> handlers;

    public KeyIdentityLookupStrategy(HandlerLookupStrategy<K, H> other, Map<K, H> handlers)
    {
        super(other);
        this.handlers = ImmutableMap.copyOf(handlers);
    }

    @Override
    protected H doLookupHandler(K key)
    {
        return handlers.get(key);
    }


}