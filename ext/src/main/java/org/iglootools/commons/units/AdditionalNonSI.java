package org.iglootools.commons.units;

import javax.measure.quantity.Area;
import javax.measure.unit.NonSI;
import javax.measure.unit.ProductUnit;
import javax.measure.unit.Unit;

public class AdditionalNonSI {
    public static Unit<Area> SQUARE_FOOT = new ProductUnit<Area>(NonSI.FOOT.times(NonSI.FOOT));
}
