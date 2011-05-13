package org.iglootools.commons.web.struts2;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.ValueStack;

public class MultipleModelDrivenInterceptor extends AbstractInterceptor {
    private static final long serialVersionUID = 1L;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        Object action = invocation.getAction();

        if (action instanceof MultipleModelDriven) {
            MultipleModelDriven modelDriven = (MultipleModelDriven) action;
            ValueStack stack = invocation.getStack();
            if (modelDriven.getModels() !=  null) {
                for(Object o : modelDriven.getModels()) {
                    if(o != null) {
                        stack.push(o);
                    }
                }
            }
        }
        return invocation.invoke();
    }
}