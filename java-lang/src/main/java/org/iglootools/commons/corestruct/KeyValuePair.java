package org.iglootools.commons.corestruct;

import com.google.common.base.Objects;

/**
 * @author Sami Dalouche (sami.dalouche@gmail.com)
 */
public final class KeyValuePair<K, V> {
    private K key;
    private V value;

    public KeyValuePair() {
        super();
    }

    public KeyValuePair(K name, V value) {
        super();
        this.key = name;
        this.value = value;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final KeyValuePair other = (KeyValuePair) obj;

        return Objects.equal(key, other.key) && Objects.equal(value, other.value);
    }

    /*
     * (non-Javadoc)
     *
     * @see funala.utils.IKeyValuePair#getKey()
     */
    public K getKey() {
        return this.key;
    }

    /*
     * (non-Javadoc)
     *
     * @see funala.utils.IKeyValuePair#getValue()
     */
    public V getValue() {
        return this.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key, value);
    }

    @Override
    public String toString() {
        return "(" + this.key + "," + this.value + ")";
    }
}
