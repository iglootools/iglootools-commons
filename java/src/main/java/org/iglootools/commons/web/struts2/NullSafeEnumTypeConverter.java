package org.iglootools.commons.web.struts2;

import com.opensymphony.xwork2.conversion.impl.EnumTypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * <p>
 * Null Safe version of {@link EnumTypeConverter}. Anything that does not match
 * the enumeration is going to be converted to null.
 * </p>
 *
 * @author Sami Dalouche (sami.dalouche@gmail.com)
 *
 */
@Deprecated
public class NullSafeEnumTypeConverter extends EnumTypeConverter {
    Logger logger = LoggerFactory.getLogger(NullSafeEnumTypeConverter.class);

    @SuppressWarnings("unchecked")
    @Override
    public Object convertValue(Map context, Object o, Class toClass) {
	try {
	    return super.convertValue(context, o, toClass);
	} catch (IllegalArgumentException e) {
	    // Null value
	    return null;
	}

    }
}