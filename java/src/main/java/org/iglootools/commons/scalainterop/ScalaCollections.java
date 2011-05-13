package org.iglootools.commons.scalainterop;


import com.google.common.collect.Sets;

/**
 * @author Sami Dalouche (sami.dalouche@gmail.com)
 */
public class ScalaCollections {
    public static <T> scala.collection.immutable.Set<T> toScalaSet(java.util.Set<T> javaSet) {
       return (scala.collection.immutable.Set<T>) ScalaCollectionsImpl.toScalaSet(javaSet);
    }

    public static <T> scala.collection.immutable.Set<T> toScalaSet(java.lang.Iterable<? extends T> iterable) {
       return (scala.collection.immutable.Set<T>) ScalaCollectionsImpl.toScalaSet(Sets.newHashSet(iterable));
    }
}
