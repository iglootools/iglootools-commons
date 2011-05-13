package org.iglootools.commons.units;

import junit.framework.Assert;
import org.junit.Test;

import javax.measure.unit.NonSI;
import javax.measure.unit.SI;

import static org.junit.Assert.assertEquals;

public class DistanceUnitTest {

    @Test
    public void shouldCreateFromKiloGram() {
        DistanceUnit unit = DistanceUnit.createFrom(SI.KILO(SI.METER));
        Assert.assertEquals(DistanceUnit.KILOMETER, unit);
    }

    @Test
    public void shouldCreateFromMile() {
        DistanceUnit unit = DistanceUnit.createFrom(NonSI.MILE);
        Assert.assertEquals(DistanceUnit.MILE, unit);
    }

    @Test(expected=IllegalArgumentException.class)
    public void createFromNonRecognizedUnitShouldThrowException() {
        DistanceUnit.createFrom(SI.METER);
    }

    @Test(expected=IllegalArgumentException.class)
    public void createFromNullShouldThrowException() {
        DistanceUnit.createFrom(null);
    }

    @Test
    public void kilometerShouldConvertToJscienceUnit() {
        assertEquals(SI.KILO(SI.METER), DistanceUnit.KILOMETER.toUnit());
    }

    @Test
    public void mileShouldConvertToJscienceUnit() {
        assertEquals(NonSI.MILE, DistanceUnit.MILE.toUnit());
    }

}
