package org.iglootools.commons.joda;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * Utilitary class that provides a few Utilities to deal with GMT
 * methods
 *
 * @author Sami Dalouche (sami.dalouche@gmail.com)
 */
public class DateTimes {
    /**
     * The time zone that is used for all dates on this system
     */
    public static DateTimeZone TIME_ZONE = DateTimeZone.UTC;

    /**
     * @return GMT Date
     */
    public static DateTime currentUtcDateTime() {
        return new DateTime(TIME_ZONE);
    }

        /**
     * @return GMT Date
     */
    public static DateTime currentUtcDateTimeAtMidnight() {
        return narrowToMidnight(new DateTime(TIME_ZONE));
    }

    /**
     * Narrow an end date by using the next day's midnight's time, in order to
     * improve caching
     *
     * @return
     */
    public static DateTime narrowToNextDayNidnight(DateTime originalTime) {
        return new DateMidnight(originalTime).plusDays(1).toDateTime();
    }

    /**
     * Narrow a start date by using midnight's time, in order to improve caching
     *
     * @return
     */
    public static DateTime narrowToMidnight(DateTime originalTime) {
        return new DateMidnight(originalTime).toDateTime();
    }
}
