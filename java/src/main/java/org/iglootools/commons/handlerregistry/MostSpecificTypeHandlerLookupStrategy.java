package org.iglootools.commons.handlerregistry;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Map.Entry;


public final class MostSpecificTypeHandlerLookupStrategy<H> extends AbstractLookupStrategy<Class<?>, H> {
    private ImmutableMap<Class<?>, H> handlers;

    public MostSpecificTypeHandlerLookupStrategy(HandlerLookupStrategy<Class<?>, H> other, Map<Class<?>, H> handlers)
    {
        super(other);
        this.handlers = ImmutableMap.copyOf(handlers);
    }

    @Override
    protected H doLookupHandler(Class<?> targetClass)
    {
        H mostSpecificHandler = null;
        Class<?> currentlyMostSpecificClass = null;

        for(Entry<Class<?>, H>  e : handlers.entrySet()) {
            // only assign if more specific
            if(prospectClass(e).isAssignableFrom(targetClass)
                    && (noHandlerFound(currentlyMostSpecificClass)
                    || moreSpecific(prospectClass(e), than(currentlyMostSpecificClass)))) {
                currentlyMostSpecificClass = prospectClass(e);
                mostSpecificHandler = e.getValue();
            }
        }

        return mostSpecificHandler;
    }

    private <K> K than(K k)
    {
        return k;
    }

    private Class<?> prospectClass(Entry<Class<?>, H> e)
    {
        return e.getKey();
    }

    private boolean moreSpecific(Class<?> prospectClass, Class<?> currentMostSpecificClass)
    {
        return currentMostSpecificClass.isAssignableFrom(prospectClass);
    }

    private boolean noHandlerFound(Class<?> foundClass)
    {
        return foundClass == null;
    }

}