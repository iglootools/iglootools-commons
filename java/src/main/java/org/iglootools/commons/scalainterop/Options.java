package org.iglootools.commons.scalainterop;

import scala.None$;
import scala.Option;
import scala.Some;

/**
 * @author Sami Dalouche (sami.dalouche@gmail.com)
 */
public class Options {
    public static <T> Option<T> none(Class<T> clazz) {
        return (Option<T>) scala.None$.MODULE$;
    }

    public static <T>  Option<T> some(T o) {
        return new Some<T>(o);
    }

    public static <T> Option<T> toOption(T o) {
        if(o == null) {
            return (Option<T>) none(Object.class);
        } else {
            return some(o);
        }
    }

    public static <T> T fromOption(Option<T> option) {
        return fromOption(option, null);
    }
    public static <T> T fromOption(Option<T> option, T defaultValue) {
        if(option.isDefined()) {
            return (T) option.get();
        } else {
            return defaultValue;
        }
    }
}
