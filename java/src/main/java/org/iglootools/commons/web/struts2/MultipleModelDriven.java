package org.iglootools.commons.web.struts2;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * Same as {@link ModelDriven}, except 
 * that it is possible to push several models on the {@link ValueStack}
 *
 * @author samokk
 *
 */
public interface MultipleModelDriven {
    public Iterable<?> getModels();
}
