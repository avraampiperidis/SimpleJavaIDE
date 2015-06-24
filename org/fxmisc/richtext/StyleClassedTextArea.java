package org.fxmisc.richtext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Text area that uses style classes to define style of text segments.
 */
public class StyleClassedTextArea extends StyledTextArea<Collection<String>> {

    public <C> StyleClassedTextArea(boolean preserveStyle) {
        super(Collections.<String>emptyList(),
                (text, styleClasses) -> text.getStyleClass().addAll(styleClasses),
                preserveStyle);
    }

    /**
     * Creates a text area with empty text content.
     */
    public StyleClassedTextArea() {
        this(true);
    }

    /**
     * Convenient method to assign a single style class.
     */
    public void setStyleClass(int from, int to, String styleClass) {
        List<String> styleClasses = new ArrayList<>(1);
        styleClasses.add(styleClass);
        setStyle(from, to, styleClasses);
    }
}
