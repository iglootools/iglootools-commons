package org.iglootools.commons.web.struts2;

import org.apache.struts2.util.StrutsTypeConverter;
import org.iglootools.commons.corestruct.KeyValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.StringTokenizer;


public class KeyValuePairTypeConverter extends StrutsTypeConverter {
    private static Logger logger = LoggerFactory.getLogger(KeyValuePairTypeConverter.class);

    @SuppressWarnings("unchecked")
    @Override
    public Object convertFromString(Map context, String[] values, Class toClass) {
        if (toClass == KeyValuePair.class) {
            if (logger.isDebugEnabled()) {
                logger.debug(context.toString());
                logger.debug(values.toString());
                logger.debug(toClass.toString());
            }

            String str = values[0];
            StringTokenizer token = new StringTokenizer(str, ",");
            String key = token.nextToken();
            String val = token.nextToken();
            KeyValuePair<String, Integer> res = new KeyValuePair<String, Integer>(
                    key, new Integer(val));
            if (logger.isDebugEnabled())
                logger.debug("will return :" + res);

            return res;
        }

        if (logger.isDebugEnabled())
            logger.debug("will return null");

        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String convertToString(Map arg0, Object arg1) {
        logger.debug("convert to string : " + arg1);

        if (arg1 instanceof KeyValuePair) {
            KeyValuePair<String, Integer> kv = (KeyValuePair<String, Integer>) arg1;

            return "" + kv.getKey() + "," + kv.getValue();
        }

        return null;
    }
}