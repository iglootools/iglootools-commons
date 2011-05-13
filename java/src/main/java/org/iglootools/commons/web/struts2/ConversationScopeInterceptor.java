package org.iglootools.commons.web.struts2;


import com.google.common.base.Strings;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.struts2.StrutsStatics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * <!-- START SNIPPET: description -->
 *
 * An interceptor that implements a conversation scope. Allowing a user to have multiple conversations
 * of the same type in process at the same time. This avoids the collision that would take place if
 * you used a session scoped "model" (like Struts 2) or session scope form (like Struts 1).
 *
 * This borrows a lot from the ModelDriven concept. Except that it implements a means of
 * transparently swapping the "conversationModel" into and out of the Action. It is highly advised that
 * you use a JavaBean to back your conversation model and not a Map. Using a Map can result in strange
 * behaviour due to OGNL setting properties on the Map from the users request, but not performing any
 * type conversion. Using a JavaBean avoids this confusion and will force OGNL to do type conversion,
 * which is what you generally want.
 *
 * It is also similar to ScopeInterceptor, except that it allows mutiple moderls serving the same purpose
 * to be stored in the users session. It is also simpler because you do not need to specify the name
 * of the model, nor its class. In this sense it is far more like ModelDriven.
 *
 * <!-- END SNIPPET: description -->
 *
 * <p/> <u>Interceptor parameters:</u>
 *
 * <!-- START SNIPPET: parameters -->
 *
 * <ul>
 *
 * <li>None</li>
 *
 * </ul>
 *
 * <!-- END SNIPPET: parameters -->
 *
 * <p/> <u>Extending the interceptor:</u>
 *
 * <p/>
 *
 * <!-- START SNIPPET: extending -->
 *
 * There are no known extension points for this interceptor.
 *
 * <!-- END SNIPPET: extending -->
 *
 * <p/> <u>Example code:</u>
 *
 * <pre>
 * <!-- START SNIPPET: example -->
 * &lt;action name="someAction" class="com.examples.SomeAction"&gt;
 *     &lt;interceptor-ref name="conversation-interceptor"/&gt;
 *     &lt;interceptor-ref name="basicStack"/&gt;
 *     &lt;result name="success"&gt;good_result.ftl&lt;/result&gt;
 * &lt;/action&gt;
 * </pre>
 *
 * In your view you need to have a hidden element named "S2_CONVERSATION_ID". This id is
 * set on the value stack under #s2cmodelid.
 *
 * <pre>
 * &lt;s:hidden name="S2_CONVERSATION_ID" value="%{#s2cmodelid}" / &gt;
 * </pre>
 *
 * Additionally the conversation model is accessible using #s2cmodel.
 *
 * <pre>
 * &lt;s:property value="#s2cmodel.someProperty" / &gt;
 * </pre>
 *
 *
 * <!-- END SNIPPET: example -->
 *
 * @see ModelDriven
 * @see org.apache.struts2.interceptor.ScopeInterceptor
 */

public class ConversationScopeInterceptor extends AbstractInterceptor implements StrutsStatics {

    private final static Logger logger = LoggerFactory.getLogger(ConversationScopeInterceptor.class);
    private static final String CONVERSATION_COUNT = "S2_CONVERSATION_COUNT";
    private int conversationCount = 0;

    public ConversationScopeInterceptor () {
        super ();
    }

    public String intercept (ActionInvocation invocation) throws Exception {
        String result = null;
        final Object action = invocation.getAction ();

        if (action instanceof ConversationScopeAware) {
            final ActionContext context = invocation.getInvocationContext ();
            ConversationScopeAware cAction = (ConversationScopeAware) action;
            HttpServletRequest request = (HttpServletRequest) context.get(HTTP_REQUEST);
            HttpSession session =  request.getSession (true);


            Map conversationMap = (Map) session.getAttribute (ConversationScopeAware.S2_CONVERSATION_SCOPE);
            if (conversationMap == null) {
                logger.debug(String.format("[SessionID: %s] Global Conversation Map does not currently exist in the session, creating new one.", session.getId()));
                conversationMap = new HashMap ();
                session.setAttribute (ConversationScopeAware.S2_CONVERSATION_SCOPE, conversationMap);
            }

            if(logger.isDebugEnabled()) {
                Map mapInSession = (Map) session.getAttribute(ConversationScopeAware.S2_CONVERSATION_SCOPE);
                logger.debug(String.format("[SessionID: %s] Map Currently in Session: %s",session.getId(),(mapInSession != null ? mapInSession.toString() : "null")));
            }

            Object conversationIdParam = context.getParameters().remove(ConversationScopeAware.S2_CONVERSATION_ID);

            String conversationId = null;
            if (conversationIdParam != null && conversationIdParam.getClass().isArray() && ((Object[]) conversationIdParam).length == 1) {
                conversationId = (String) ((Object[]) conversationIdParam)[0];
                if(logger.isDebugEnabled()) {
                    logger.debug(String.format("[SessionID: %s] Using Conversation: %s",  session.getId(), conversationId));
                }
            }

            if (Strings.isNullOrEmpty(conversationId) ) {
                conversationId = getConversationId (session);
                if(logger.isDebugEnabled()) {
                    logger.debug(String.format("[SessionID: %s] ConversationID is blank. Generated a new one: %s", session.getId(), conversationId));
                }
            }

            cAction.setConversationId (conversationId);

            Object conversationModel = conversationMap.get (conversationId);

            // If the conversationModel is null (ie. it's not in the conversationMap) then this is the start of a conversaion,
            // and we need to prepare the conversationModel and save it in the conversationMap.
            if (conversationModel == null) {
                if(logger.isDebugEnabled()) {
                    logger.debug(String.format("[SessionID: %s][ConversationID: %s] ConversationModel does not exist Yet. Asking action to prepareConversationModel()", session.getId(), conversationId));
                }
                conversationModel = cAction.prepareConversationModel (); // The action needs to maintain a copy of the model after this call, therefore we don't set it on the action.
                conversationMap.put (conversationId, conversationModel);

            } else {
                if(logger.isDebugEnabled()) {
                    logger.debug(String.format("[SessionID: %s][ConversationID: %s] The conversationModel was in the session. We need to set it on the action.", session.getId(), conversationId));
                }
                cAction.setConversationModel (conversationModel);
                if(logger.isDebugEnabled()) {
                    logger.debug(String.format("[SessionID: %s][ConversationID: %s] ConversationModel set in the session", session.getId(), conversationModel.toString()));
                }
            }

            context.getValueStack().push (conversationModel);

            result = invocation.invoke ();

            if (cAction.isConversationFinished () ) {
                logger.debug("Conversation Finished. Removing Conversation from session");
                conversationMap.remove (conversationId);
            }

            forceFlushOfConversationMapToSession(session, conversationMap);

        } else {
            result = invocation.invoke ();
        }
        return result;
    }

    /**
     * Make sure to always run this after each manipulation of the session.
     *
     * @param session
     * @param conversationMap
     */
    private void forceFlushOfConversationMapToSession(HttpSession session, Map conversationMap) {
        logger.debug("Flushing COnversation Map to Session");
        session.setAttribute (ConversationScopeAware.S2_CONVERSATION_SCOPE, conversationMap);
    }

    private synchronized String getConversationId (HttpSession session) {
        /*
          // If the conversationCount is not incrementing it could be caused by a bug in the S2 that causes interceptors to be reinstantiated. If that happens uncomment
          // this and comment the current method out, rebuild and run.
          Integer conversationCount = (Integer) session.getServletContext().getAttribute (CONVERSATION_COUNT);
          if (conversationCount == null) {
              conversationCount = 0;
          }
          conversationCount = conversationCount.intValue() + 1;
          session.getServletContext().setAttribute (CONVERSATION_COUNT, conversationCount);
          return "CID-" + conversationCount + "-" + System.currentTimeMillis ();
       */
        return "CID" + this.conversationCount++ + System.currentTimeMillis ();
    }
}
