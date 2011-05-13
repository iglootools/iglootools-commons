package org.iglootools.commons.corestruct;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class RangeTest {

    @Test
    public void shouldBeEqual() {
        Range<Integer> intRange = new Range<Integer>(5, 10);
        Range<Integer> intRange2 = new Range<Integer>(5, 10);
        assertEquals(intRange, intRange2);
        assertEquals(intRange.hashCode(), intRange2.hashCode());
    }

    @Test
    public void shouldNotBeEqualBecauseOfMax() {
        Range<Integer> intRange = new Range<Integer>(5, 10);
        Range<Integer> intRange2 = new Range<Integer>(5, 11);
        assertFalse(intRange.equals(intRange2));
        assertFalse(intRange.hashCode() == intRange2.hashCode());
    }

    @Test
    public void shouldNotBeEqualBecauseOfMin() {
        Range<Integer> intRange = new Range<Integer>(5, 10);
        Range<Integer> intRange2 = new Range<Integer>(6, 10);
        assertFalse(intRange.equals(intRange2));
        assertFalse(intRange.hashCode() == intRange2.hashCode());
    }

    @Test
    public void shouldCreateRange() {
        Range<Integer> intRange = new Range<Integer>(5, 10);
        assertThat(intRange.getMin(), is(new Integer(5)));
        assertThat(intRange.getMax(), is(new Integer(10)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAllowGreaterMinThanMax() {
        new Range<Integer>(50, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAllowCreationWithNullMinAndMax() {
        new Range<Integer>(null, null);
    }

    public void shouldCreateRangeWithNullMin() {
        Range<Integer> intRange = new Range<Integer>(null, 10);
        assertThat(intRange.getMin(), is(nullValue()));
        assertThat(intRange.getMax(), is(new Integer(10)));
    }

    public void shouldCreateRangeWithNullMax() {
        Range<Integer> intRange = new Range<Integer>(5, null);
        assertThat(intRange.getMin(), is(new Integer(10)));
        assertThat(intRange.getMax(), is(nullValue()));
    }
}
