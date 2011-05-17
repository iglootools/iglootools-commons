package org.iglootools.commons.joda;

import org.hamcrest.CoreMatchers;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Sami Dalouche (sami.dalouche@gmail.com)
 */
public class DateTimesTest {
    @Test
    public void shouldNarrowToMidnight() {
        DateTime dt = new DateTime().withYear(2008).withMonthOfYear(01).withDayOfMonth(5);
        DateTime expected = new DateTime().withYear(2008).withMonthOfYear(01).withDayOfMonth(5).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
        Assert.assertThat(DateTimes.narrowToMidnight(dt), CoreMatchers.is(expected));
    }

    @Test
    public void shouldNarrowToNextDayMidnight() {
        DateTime dt = new DateTime().withYear(2008).withMonthOfYear(01).withDayOfMonth(5);
        DateTime expected = new DateTime().withYear(2008).withMonthOfYear(01).withDayOfMonth(6).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
        Assert.assertThat(DateTimes.narrowToNextDayNidnight(dt), CoreMatchers.is(expected));
    }
}
