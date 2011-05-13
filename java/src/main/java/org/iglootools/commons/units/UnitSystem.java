package org.iglootools.commons.units;

import javax.measure.quantity.Area;
import javax.measure.quantity.Length;
import javax.measure.unit.NonSI;


/**
 * See <a href="http://www.convert-me.com/en/convert/volume">Unit Systems</a>
 *
 * @author Sami Dalouche (sami.dalouche@gmail.com)
 *
 */
public enum UnitSystem {
    /**
     * <a href="http://en.wikipedia.org/wiki/Metric_system">The International
     * System of Units (SI)</a>
     */
    SI {
        @Override
        public javax.measure.unit.Unit<Area> preferredAreaUnit() {
            return javax.measure.unit.SI.SQUARE_METRE;
        }

        @Override
        public javax.measure.unit.Unit<Length> preferredDistanceUnit() {
            return javax.measure.unit.SI.KILO(javax.measure.unit.SI.METRE);
        }
    },
    /**
     * <a href="http://en.wikipedia.org/wiki/Imperial_unit">British Imperial</a>
     */
    BRITISH_IMPERIAL{
        @Override
        public javax.measure.unit.Unit<Area> preferredAreaUnit() {
            return AdditionalNonSI.SQUARE_FOOT;
        }
        @Override
        public javax.measure.unit.Unit<Length> preferredDistanceUnit() {
            return NonSI.MILE;
        }
    },
    /**
     * <a href="http://en.wikipedia.org/wiki/US_customary_units">US Cutomary
     * Units</a>
     */
    US_CUSTOMARY{
        @Override
        public javax.measure.unit.Unit<Area> preferredAreaUnit() {
            return AdditionalNonSI.SQUARE_FOOT;
        }
        @Override
        public javax.measure.unit.Unit<Length> preferredDistanceUnit() {
            return NonSI.MILE;
        }
    };
    /*
     * We should add Chinese Imperial, Japanese and Thai Systems
     */

    public abstract javax.measure.unit.Unit<Area> preferredAreaUnit();
    public abstract javax.measure.unit.Unit<Length> preferredDistanceUnit();
}
