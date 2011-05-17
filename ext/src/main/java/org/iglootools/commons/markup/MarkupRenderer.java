package org.iglootools.commons.markup;

/**
 * Parses and renders a markup language, and returns XHTML. All free-form text
 * areas must be rendered using this Markup Parser !!!!
 *
 * @author Sami Dalouche (sami.dalouche@gmail.com)
 *
 */
public interface MarkupRenderer {

    /**
     * Parses source, and returns the generated XHTML
     *
     * @param source
     * @return
     */
    public String render(String source);
}
