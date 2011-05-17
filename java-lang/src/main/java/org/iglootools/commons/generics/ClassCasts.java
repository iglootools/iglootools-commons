package org.iglootools.commons.generics;

/**
 * @author Sami Dalouche (sami.dalouche@gmail.com)
 */
public class ClassCasts {
    public static <T,F extends T> Class<T> cast(Class<F> from, Class<T> to) {
        return (Class<T>) from;
    }

    public static <T> Class<T> from(Class<T> c) {
        return c;
    }

    public static <T> Class<T> to(Class<T> c) {
        return c;
    }
}
