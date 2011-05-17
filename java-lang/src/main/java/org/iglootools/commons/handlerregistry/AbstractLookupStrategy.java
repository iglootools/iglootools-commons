package org.iglootools.commons.handlerregistry;

public abstract class AbstractLookupStrategy<K,H> implements HandlerLookupStrategy<K,H> {
    private HandlerLookupStrategy<K,H> other;

    public AbstractLookupStrategy(HandlerLookupStrategy<K,H> other)
    {
        super();
        this.other = other;
    }

    public final H findHandlerFor(K key) {
        H handler = doLookupHandler(key);
        if(handler == null && other != null)
        {
            handler = other.findHandlerFor(key);
            doAfterOtherLookup(key, handler);
        }
        return handler;
    }

    protected abstract H doLookupHandler(K key);

    protected void doAfterOtherLookup(K key, H foundHandler)
    {

    }
}