package org.iglootools.commons.web.struts2;

import ognl.DefaultTypeConverter;
import org.apache.struts2.util.StrutsTypeConverter;

import java.util.Map;

/**
 * The same as {@link StrutsTypeConverter}, except that it handles null values
 * correctly
 *
 * @author Sami Dalouche (sami.dalouche@gmail.com)
 *
 * FIXME : does not work ??
 */
public class NullSafeTypeConverter extends DefaultTypeConverter {

    @SuppressWarnings("unchecked")
    @Override
    public Object convertValue(Map context, Object o, Class toClass) {
	if (o instanceof String[]) {
	    String s = ((String[]) o)[0];
	    if (returnNullIfNecessary(s)) {
		return null;
	    }
	} else if (o instanceof String) {
	    if (returnNullIfNecessary(o)) {
		return null;
	    }
	    return returnNullIfNecessary(o);
	}

	return super.convertValue(context, o, toClass);
    }

    private boolean returnNullIfNecessary(Object o) {

	if (o == null || "".equals(o)) {
	    return true;
	}
	return false;
    }

}