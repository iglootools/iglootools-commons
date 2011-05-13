package org.iglootools.commons.units;

import org.junit.Assert;
import org.junit.Test;

import javax.measure.unit.SI;

public class AreaUnitTest {

    @Test(expected=IllegalArgumentException.class)
    public void createFromNonRecognizedUnitShouldThrowException() {
        AreaUnit.createFrom(SI.KILO(SI.SQUARE_METRE));
    }

    @Test(expected=IllegalArgumentException.class)
    public void createFromNullShouldThrowException() {
        AreaUnit.createFrom(null);
    }

    @Test
    public void shouldCreateFromSquareFoot() {
        AreaUnit unit = AreaUnit.createFrom(AdditionalNonSI.SQUARE_FOOT);
        Assert.assertEquals(AreaUnit.SQUARE_FOOT, unit);
    }

    @Test
    public void shouldCreateFromSquareMeter() {
        AreaUnit unit = AreaUnit.createFrom(SI.SQUARE_METRE);
        Assert.assertEquals(AreaUnit.SQUARE_METER, unit);
    }

    @Test
    public void shouldCreateJscienceUnitWithSquareFeet() {
        Assert.assertEquals(AdditionalNonSI.SQUARE_FOOT, AreaUnit.SQUARE_FOOT.toUnit());
    }

    @Test
    public void shouldCreateJScienceUnitWithSquareMeters() {
        Assert.assertEquals(SI.SQUARE_METRE, AreaUnit.SQUARE_METER.toUnit());
    }
}
