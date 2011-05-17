package org.iglootools.commons.handlerregistry;

import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;

public final class HandlerRegistries
{
    public static class HandlerRegistryBuilder<K,H>
    {
        private Map<K, H> handlers = new HashMap<K, H>();

        public HandlerRegistryBuilder<K, H> addHandler(K key, H handler)
        {
            Preconditions.checkArgument(key != null, "key is required");
            Preconditions.checkArgument(handler != null, "handlerregistry is required");
            handlers.put(key, handler);
            return this;
        }

        public HandlerRegistryBuilder<K, H> addHandlerForSeveralKeys(Iterable<? extends K> keys, H handler)
        {
            Preconditions.checkArgument(keys != null, "keys is required");
            for(K key : keys)
            {
                addHandler(key, handler);
            }
            return this;
        }

        public HandlerRegistry<Class<?>, H> buildTypeRegistry()
        {
            return new DefaultHandlerRegistry<Class<?>, H>(typeHandlerLookupStrategy((Map<Class<?>, H>) handlers));
        }

        public HandlerRegistry<K, H> buildSimpleRegistry()
        {
            return new DefaultHandlerRegistry<K, H>(simpleHandlerLookupStrategy(handlers));
        }

    }

    public static <H> HandlerRegistryBuilder<Class<?>, H> typeHandlerRegistry(Class<H> type)
    {
        return new HandlerRegistryBuilder<Class<?>, H>();
    }

    public static <H> HandlerRegistry<Class<?>, H> typeHandlerRegistry(Map<Class<?>, H> handlers)
    {
        return new DefaultHandlerRegistry<Class<?>, H>(typeHandlerLookupStrategy(handlers));
    }

    public static <K, H> HandlerRegistryBuilder<K, H> simpleHandlerRegistry(Class<K> keyType, Class<H> handlerType)
    {
        return new HandlerRegistryBuilder<K, H>();
    }

    public static <K, H> HandlerRegistry<K, H> simpleHandlerRegistry(Map<K,H> handlers)
    {
        return new DefaultHandlerRegistry<K, H>(simpleHandlerLookupStrategy(handlers));
    }

    private static <H> HandlerLookupStrategy<Class<?>,H> typeHandlerLookupStrategy(Map<Class<?>, H> handlers)
    {
        return new CacheLookupStrategy<Class<?>, H>(new MostSpecificTypeHandlerLookupStrategy<H>(null, handlers));
    }

    private static <K, H> HandlerLookupStrategy<K,H> simpleHandlerLookupStrategy(Map<K, H> handlers)
    {
        return new KeyIdentityLookupStrategy<K,H>(null, handlers);
    }
}
