package org.iglootools.commons.handlerregistry;

public interface HandlerRegistry<T, H>
{
    /**
     *
     * @param key
     * @return the handlerregistry associated to the given key otherwise
     * @throws NoMatchingHandlerFoundException if no handlerregistry matching the given key is found
     */
    H findHandlerFor(T key);

}
