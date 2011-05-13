package org.iglootools.commons.units;

import org.junit.Assert;
import org.junit.Test;

import javax.measure.unit.NonSI;
import javax.measure.unit.SI;


public class UnitSystemTest {

    @Test
    public void preferredAreaUnitWithBritishUnitSystemShouldBeSquareFoot() {
        Assert.assertEquals(AdditionalNonSI.SQUARE_FOOT, UnitSystem.BRITISH_IMPERIAL.preferredAreaUnit());
    }

    @Test
    public void preferredAreaUnitWithSiUnitSystemShouldBeSquareMeter() {
        Assert.assertEquals(SI.SQUARE_METRE, UnitSystem.SI.preferredAreaUnit());
    }

    @Test
    public void preferredAreaUnitWithUsUnitSystemShouldBeSquateFoot() {
        Assert.assertEquals(AdditionalNonSI.SQUARE_FOOT, UnitSystem.US_CUSTOMARY.preferredAreaUnit());
    }

    @Test
    public void preferredDistanceUnitWithBritishUnitSystemShouldBeMile() {
        Assert.assertEquals(NonSI.MILE, UnitSystem.BRITISH_IMPERIAL.preferredDistanceUnit());
    }

    @Test
    public void preferredDistanceUnitWithSiUnitSystemShouldBeMetre() {
        Assert.assertEquals(SI.KILO(SI.METRE), UnitSystem.SI.preferredDistanceUnit());
    }

    @Test
    public void preferredDistanceUnitWithUsUnitSystemShouldBeMile() {
        Assert.assertEquals(NonSI.MILE, UnitSystem.US_CUSTOMARY.preferredDistanceUnit());
    }
}
