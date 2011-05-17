package org.iglootools.commons.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Sami Dalouche (sami.dalouche@gmail.com)
 */
public class EnhancedResourceBundle {
    public static EnhancedResourceBundle getBundle(String name, Locale locale) {
        return new EnhancedResourceBundle(name, locale);
    }

    private final ResourceBundle resourceBundle;

    public EnhancedResourceBundle(String name, Locale locale) {
        this.resourceBundle = ResourceBundle.getBundle(name, locale);
    }

    public String getString(String key) {
        return resourceBundle.getString(key);
    }

    public String getString(String key, Object... params) {
        return MessageFormat.format(getString(key), params);
    }
}
