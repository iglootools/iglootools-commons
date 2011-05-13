package org.iglootools.commons.web;

import org.junit.Assert;
import org.junit.Test;


public class UrlsTest {

    @Test
    public void testEncodeUrlUsingHtmlFormEncoding() {
        String urlToEncode = "http://sirika.com:9090/funala/citySearch.action?searchQuery=a&format=rss";
        String encodedUrl = Urls.encodeUrlUsingHtmlFormEncoding(urlToEncode);
        Assert
                .assertEquals(
                        "http%3A%2F%2Fsirika.com%3A9090%2Ffunala%2FcitySearch.action%3FsearchQuery%3Da%26format%3Drss",
                        encodedUrl);
    }


}
