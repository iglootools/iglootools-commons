package org.iglootools.commons.units;

import org.junit.Test;

import javax.measure.unit.NonSI;
import javax.measure.unit.SI;

import static org.junit.Assert.assertEquals;

public class FriendlyConverterFactoryTest {

    @Test
    public void GetFriendlyConverterFromKmToMile() {
        FriendlyConverter<Double> converter = FriendlyConverterFactory.createFriendlyDistanceConverter(SI.KILO(SI.METER), NonSI.MILE);
        double tenMiles = converter.convert(15.1d);
        double twentyMiles = converter.convert(30.2d);
        double fiftyMiles = converter.convert(75.5d);
        assertEquals(10d, tenMiles, 0.1);
        assertEquals(20d, twentyMiles, 0.1);
        assertEquals(45d, fiftyMiles, 0.1);
    }
}
