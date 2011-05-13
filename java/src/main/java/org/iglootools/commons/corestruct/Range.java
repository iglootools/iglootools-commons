package org.iglootools.commons.corestruct;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * @author Sami Dalouche (sami.dalouche@gmail.com)
 */
@SuppressWarnings("unchecked")
public final class Range<T extends Comparable> {
    private T min;
    private T max;

    public Range(T min, T max) {
        super();
        Preconditions.checkArgument(min != null || max != null, "Min and Max cannot be null at the same time");
        if (min != null && max != null) {
            Preconditions.checkArgument(min.compareTo(max) <= 0, "Min must be lesser than Max");
        }

        this.min = min;
        this.max = max;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Range other = (Range) obj;
        return Objects.equal(min, other.min) && Objects.equal(max, other.max);
    }

    public T getMax() {
        return max;
    }

    public T getMin() {
        return min;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(min, max);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(min).append("-").append(max)
                .toString();
    }

}