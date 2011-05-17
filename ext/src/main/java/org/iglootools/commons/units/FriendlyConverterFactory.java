package org.iglootools.commons.units;

import javax.measure.quantity.Length;
import javax.measure.unit.Unit;

public class FriendlyConverterFactory {
    /**
     * The current rules are fine for Mile and Km. Might or might not be ok for
     * other units
     * <ul>
     * <li>14.1 is rounded to 15</li>
     * <li>11.5 is rounded to 10</li>
     * <li>17 is rounded to 20</li>
     * </ul>
     *
     * @param from
     * @param to
     * @return
     */
    public static FriendlyConverter<Double> createFriendlyDistanceConverter(final Unit<Length> from, final Unit<Length> to) {
        return new FriendlyConverter<Double>() {
            public Double convert(final Double value) {
                long convertedAndRounded = Math.round(from.getConverterTo(to).convert(value));
                Double result = null;

                long mod = convertedAndRounded % 10;
                if (mod < 2.5) {
                    result = new Double(convertedAndRounded - mod);
                } else if (mod < 7.5) {
                    result = new Double(convertedAndRounded - mod + 5);
                } else {
                    result = new Double(convertedAndRounded - mod + 10);
                }
                return result;
            }
        };
    }
}