package org.iglootools.commons.joda;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.joda.time.DateTime;
import org.joda.time.Seconds;

public class NowMatcher extends BaseMatcher<DateTime> {
    public static NowMatcher now() {
        return new NowMatcher();
    }

    public static NowMatcher now(int maxSeconds) {
        return new NowMatcher(maxSeconds);
    }

    private int DEFAULT_MAX_SECONDS = 10;

    private int maxSeconds;

    public NowMatcher() {
        super();
        maxSeconds = DEFAULT_MAX_SECONDS;
    }

    public NowMatcher(int maxSeconds) {
        super();
        this.maxSeconds = maxSeconds;
    }

    public boolean matches(Object o) {
        DateTime dateTime = (DateTime) o;
        return Seconds.secondsBetween(dateTime, new DateTime()).getSeconds() < maxSeconds;
    }

    public void describeTo(Description arg0) {
    }

}
