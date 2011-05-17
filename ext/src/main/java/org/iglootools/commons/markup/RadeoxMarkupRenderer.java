package org.iglootools.commons.markup;

import org.radeox.api.engine.RenderEngine;
import org.radeox.api.engine.context.RenderContext;
import org.radeox.engine.BaseRenderEngine;
import org.radeox.engine.context.BaseRenderContext;

public class RadeoxMarkupRenderer implements MarkupRenderer {

    public String render(String source) {
        RenderContext context = new BaseRenderContext();
        RenderEngine engine = new BaseRenderEngine();
        String result = engine.render(source, context);
        return result;
    }

}
