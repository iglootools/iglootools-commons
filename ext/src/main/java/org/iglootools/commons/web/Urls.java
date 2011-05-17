package org.iglootools.commons.web;

import com.google.common.base.Charsets;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Stupid replacement for Struts2
 *
 * @author Sami Dalouche (sami.dalouche@gmail.com)
 *
 */
public class Urls {
    private final static String GOOGLE_MAP_BASE_URL = "http://maps.google.com/maps?q=";
    public final static boolean USE_SHORT_FORMAT = true;
    public final static String DEFAULT_LANGUAGE = "en";

    /**
     *
     * @param encodedRssUrl  MUST BE URL-encoded !!!!!!!!!!!!!!
     * @return
     */
    public static String createGoogleMapUrl(String baseUrl,String encodedRssUrl) {
        String replaced = replaceSpecialSymbolsWithHtmlEntities(encodedRssUrl);

        StringBuilder sb = new StringBuilder();
        sb.append(GOOGLE_MAP_BASE_URL);
        sb.append(Urls.encodeUrlUsingHtmlFormEncoding(baseUrl + replaced));

        return sb.toString();

    }


    /**
     * Simple Wrapper around {@link URLEncoder} that encodes as UTF-8
     *
     * @return
     */
    public static String encodeUrlUsingHtmlFormEncoding(String nonEncodedUrl) {
        URL url = null;
        String encodedUrl = null;
        try {
            url = new URL(nonEncodedUrl);
        } catch (MalformedURLException mue) {
            throw new RuntimeException(mue);
        }

        try {
            encodedUrl = URLEncoder.encode(url.toString(), Charsets.UTF_8.toString());
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException(uee);
        }

        return encodedUrl;
    }

    public static String replaceSpecialSymbolsWithHtmlEntities(String encodedRssUrl) {
        return encodedRssUrl.replace("&amp;", "&");
    }

}
