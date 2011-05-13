package org.iglootools.commons.units;

import javax.measure.quantity.Area;
import javax.measure.unit.SI;
import javax.measure.unit.Unit;


/**
 * Area units useable in the web actions
 *
 * @author Sami Dalouche (sami.dalouche@gmail.com)
 *
 */
public enum AreaUnit {
    SQUARE_FOOT {
        @Override
        public Unit<Area> toUnit() {
            return AdditionalNonSI.SQUARE_FOOT;
        }

    },
    SQUARE_METER {
        @Override
        public Unit<Area> toUnit() {
            return SI.SQUARE_METRE;
        }
    };

    public static AreaUnit createFrom(Unit<Area> unit) {
        /**
         * Return Kilometer by default
         */
        if (SI.SQUARE_METRE.equals(unit)) {
            return AreaUnit.SQUARE_METER;
        } else if (AdditionalNonSI.SQUARE_FOOT.equals(unit)) {
            return AreaUnit.SQUARE_FOOT;
        } else {
            throw new IllegalArgumentException("Unit Not recognized: " + unit);
        }
    }

    public abstract Unit<Area> toUnit();
}
