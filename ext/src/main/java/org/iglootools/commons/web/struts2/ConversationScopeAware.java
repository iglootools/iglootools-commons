package org.iglootools.commons.web.struts2;

public interface ConversationScopeAware {

    public static final String S2_CONVERSATION_SCOPE = "S2_CONVERSATION_SCOPE";
    public static final String S2_CONVERSATION_ID = "S2_CONVERSATION_ID";
    public static final String S2_VALUE_STACK_MODEL_KEY = "s2cmodel";
    public static final String S2_VALUE_STACK_MODEL_ID_KEY = "modelid";

    /** Get the id of the current conversation the action is handling. */
    public String getConversationId ();

    /** Set the id of the current conversation the action is handling. */
    public void setConversationId (String in);

    /** Get the conversation model. */
    public Object getConversationModel ();

    /** Set the conversation model. */
    public void setConversationModel (Object in);

    /**
     * Prepare the conversationModel. So it can be saved in the conversationScope and
     * pushed onto the top of the value stack. The action needs to maintain a reference
     * to the prepared model.
     */
    public Object prepareConversationModel ();

    /**
     * Find out if the conversation is finished. If true the conversation model can
     * be removed from the conversationScope.
     */
    public boolean isConversationFinished ();
}
