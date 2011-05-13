package org.iglootools.commons.markup;

import org.junit.Assert;
import org.junit.Test;

public class RadeoxMarkupRendererTest {
    private MarkupRenderer markupRenderer = new RadeoxMarkupRenderer();

    @Test
    public void shouldRenderSimpleString() {
        String s = markupRenderer.render("test");
        Assert.assertEquals("test", s);
    }

}
