package org.iglootools.commons.web.struts2;

import com.opensymphony.xwork2.ActionInvocation;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedOutput;
import org.apache.struts2.dispatcher.StrutsResultSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Result type to output {@link SyndFeed} objects. Can be used to output ROME
 * RSS/Atom Feeds.
 *
 * @author Sami Dalouche (sami.dalouche@gmail.com)
 *
 */
public class SyndFeedResult extends StrutsResultSupport {
    private static final long serialVersionUID = 1L;
    private static final String contentType = "text/xml";
    private static final Logger logger = LoggerFactory.getLogger(SyndFeedResult.class);
    private final String ENCODING = "UTF-8";
    private SyndFeed synFeed;

    public SyndFeedResult() {

    }

    public SyndFeedResult(String synFeedName) {
        super(synFeedName);
    }

    public SyndFeed getSynFeed() {
        return synFeed;
    }

    @Override
    protected void doExecute(String finalLocation, ActionInvocation invocation)
            throws Exception {
        OutputStream oOutput = null;
        Writer oWriter = null;
        try {
            if (synFeed == null) {
                // Find the inputstream from the invocation variable stack
                synFeed = (SyndFeed) invocation.getStack().findValue(
                        conditionalParse(finalLocation, invocation));
            }

            if (synFeed == null) {
                String msg = ("Can not find a com.sun.syndication.feed.synd.SyndFeed with the name ["
                        + finalLocation + "] in the invocation stack. " + "Check the <param name=\"synFeed\"> tag specified for this action.");
                logger.error(msg);
                throw new IllegalArgumentException(msg);
            }

            // Find the Response in context
            HttpServletResponse oResponse = (HttpServletResponse) invocation
                    .getInvocationContext().get(HTTP_RESPONSE);

            // Set the content type
            oResponse.setContentType(conditionalParse(contentType, invocation));

            // Set the content length
            // if (contentLength != null) {
            // String _contentLength = conditionalParse(contentLength,
            // invocation);
            // int _contentLengthAsInt = -1;
            // try {
            // _contentLengthAsInt = Integer.parseInt(_contentLength);
            // if (_contentLengthAsInt >= 0) {
            // oResponse.setContentLength(_contentLengthAsInt);
            // }
            // }
            // catch(NumberFormatException e) {
            // log.warn("failed to recongnize "+_contentLength+" as a number,
            // contentLength
            // header will not be set", e);
            // }
            // }

            // Set the content-disposition
            // if (contentDisposition != null) {
            // oResponse.addHeader("Content-disposition",
            // conditionalParse(contentDisposition, invocation));
            // }

            // Get the outputstream
            oOutput = oResponse.getOutputStream();

            oWriter = new OutputStreamWriter(oOutput, ENCODING);

            if (logger.isDebugEnabled()) {
                logger.debug("SynFeed result [" + finalLocation + "] type=["
                        + contentType + "]");
            }

            // Copy synfeed to output
            SyndFeedOutput output = new SyndFeedOutput();
            output.output(synFeed, oWriter);

            // Flush
            oWriter.flush();
        } finally {
            if (oWriter != null)
                oWriter.close();
            if (oOutput != null)
                oOutput.close();
        }
    }

}