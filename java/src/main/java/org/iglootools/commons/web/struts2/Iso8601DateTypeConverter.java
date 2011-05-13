package org.iglootools.commons.web.struts2;

import com.opensymphony.xwork2.conversion.TypeConversionException;
import org.apache.struts2.util.StrutsTypeConverter;
import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;
import org.joda.time.format.ISODateTimeFormat;

import java.util.Date;
import java.util.Map;

/**
 * @see <a href="http://en.wikipedia.org/wiki/ISO8601">ISO 8601</a>
 *
 *
 * @author samokk
 *
 */
public class Iso8601DateTypeConverter extends StrutsTypeConverter {

    @SuppressWarnings("unchecked")
    @Override
    public Object convertFromString(Map context, String[] o, Class toClass) {
        DateTime dt = null;

        if(o == null || o.length == 0 || o[0].length() == 0)
            return null;

        if ((DateTime.class.equals(toClass) || Date.class.equals(toClass))
                && o instanceof String[] && o.length >= 1) {
            String s = (o)[0];
            try {
                dt = ISODateTimeFormat.dateElementParser().parseDateTime(s);
            } catch (IllegalArgumentException e) {
                throw new TypeConversionException(e);
            }

        } else {
            performFallbackConversion(context, o, toClass);
        }

        if (dt == null) {
            return null;
        } else {
            if (DateTime.class.equals(toClass)) {
                return dt;
            } else {
                return dt.toDate();
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public String convertToString(Map context, Object o) {
        String res = null;
        if (o != null) {
            ReadableInstant ri = null;
            if (o instanceof Date) {
                Date d = (Date) o;
                ri = new DateTime(d);
            } else if (o instanceof ReadableInstant) {
                ri = (ReadableInstant) o;
            } else {
                performFallbackConversion(context, o, o.getClass());
            }
            res = ISODateTimeFormat.yearMonthDay().print(ri);
        }
        return res;
    }

}