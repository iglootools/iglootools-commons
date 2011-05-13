package org.iglootools.commons.units;

import javax.measure.quantity.Length;
import javax.measure.unit.NonSI;
import javax.measure.unit.SI;
import javax.measure.unit.Unit;

/**
 * Simple serializable wrapper for {@link SI#METER} and {@link NonSI#MILE}.
 *
 * <p>
 * It is used to transfer unit data to/from the web layer, and to resolve I18N
 * keys (for display/input of the units to the user)
 * </p>
 *
 * @author Sami Dalouche (sami.dalouche@gmail.com)
 *
 */
public enum DistanceUnit {
    KILOMETER {
        @Override
        public Unit<Length> toUnit() {
            return SI.KILO(SI.METER);
        }
    },
    MILE {
        @Override
        public Unit<Length> toUnit() {
            return NonSI.MILE;
        }
    };

    public static DistanceUnit createFrom(Unit<Length> unit) {
        if (SI.KILO(SI.METER).equals(unit)) {
            return DistanceUnit.KILOMETER;
        } else if (NonSI.MILE.equals(unit)) {
            return DistanceUnit.MILE;
        } else {
            throw new IllegalArgumentException("Unit Not recognized: " + unit);
        }
    }

    public abstract Unit<Length> toUnit();
}
