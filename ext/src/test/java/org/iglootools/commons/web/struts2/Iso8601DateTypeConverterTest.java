package org.iglootools.commons.web.struts2;

import com.opensymphony.xwork2.conversion.TypeConversionException;
import junit.framework.Assert;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Date;


public class Iso8601DateTypeConverterTest {
    @Test
    public void convertFromStringWithCorrectValueShouldReturnDate() {
        String from = "2007-10-09";
        Iso8601DateTypeConverter converter = new Iso8601DateTypeConverter();
        Object o = converter.convertValue(null, from, Date.class);
        Assert.assertNotNull(o);
        Assert.assertEquals(Date.class, o.getClass());
        Date date = (Date) o;
        DateTime dt = new DateTime(date);
        Assert.assertEquals(2007, dt.getYear());
        Assert.assertEquals(10, dt.getMonthOfYear());
        Assert.assertEquals(9, dt.getDayOfMonth());
    }

    @Test
    public void testConvertFromStringWithCorrectValueShouldReturnDateTime() {
        String from = "2007-10-09";
        Iso8601DateTypeConverter converter = new Iso8601DateTypeConverter();
        Object o = converter.convertValue(null, from, DateTime.class);
        Assert.assertNotNull(o);
        Assert.assertEquals(DateTime.class, o.getClass());
        DateTime dt = (DateTime) o;
        Assert.assertEquals(2007, dt.getYear());
        Assert.assertEquals(10, dt.getMonthOfYear());
        Assert.assertEquals(9, dt.getDayOfMonth());
    }

    @Test
    public void testConvertFromStringWithEmptyStringShouldReturnNull() {
        String from = "";
        Iso8601DateTypeConverter converter = new Iso8601DateTypeConverter();
        Object o = converter.convertValue(null, from, DateTime.class);
        Assert.assertNull(o);
    }

    @Test(expected=TypeConversionException.class)
    public void testConvertFromStringWithIncorrectValueUsingDateClass() {
        String from = "2007-10.09";
        Iso8601DateTypeConverter converter = new Iso8601DateTypeConverter();

        converter.convertValue(null, from, Date.class);
    }

    @Test(expected = TypeConversionException.class)
    public void testConvertFromStringWithIncorrectValueUsingDateTimeClass() {
        String from = "2007-10.09";
        Iso8601DateTypeConverter converter = new Iso8601DateTypeConverter();
        converter.convertValue(null, from, DateTime.class);
    }


    @Test
    public void testConvertToStringUsingDateClass() {
        DateTime dt = new DateTime().withYear(2007).withMonthOfYear(10)
                .withDayOfMonth(9);
        Date date = dt.toDate();
        Iso8601DateTypeConverter converter = new Iso8601DateTypeConverter();
        String s = converter.convertToString(null, date);
        Assert.assertNotNull(s);
        Assert.assertEquals("2007-10-09", s);
    }

    @Test
    public void testConvertToStringUsingDateTimeClass() {
        DateTime dt = new DateTime().withYear(2007).withMonthOfYear(10)
                .withDayOfMonth(9);
        Iso8601DateTypeConverter converter = new Iso8601DateTypeConverter();
        String s = converter.convertToString(null, dt);
        Assert.assertNotNull(s);
        Assert.assertEquals("2007-10-09", s);
    }

}