package org.iglootools.commons.units;

/**
 * A friendly Unit Converter is basically the same as a unit converter except
 * that it may be false for certain values as the priority is the display, not
 * the precision.
 *
 * <p>
 * So, for instance, a friendly unit converter could say that 15 km = 10 miles,
 * because both values are nice to display, and the result is precise enough
 * </p>
 *
 * @author samokk
 *
 * @param <T>
 */
public interface FriendlyConverter<T> {

    T convert(T value);
}