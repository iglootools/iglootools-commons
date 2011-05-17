package org.iglootools.commons.handlerregistry;

import com.google.common.base.Preconditions;

public class NoMatchingHandlerFoundException extends RuntimeException
{
    private Object key;

    public NoMatchingHandlerFoundException(Object key)
    {
        super();
        Preconditions.checkArgument(key != null, "key is required");
        this.key = key;
    }

    public Object getKey()
    {
        return key;
    }



}
