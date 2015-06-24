package org.fxmisc.richtext.skin;

import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.scene.paint.Paint;

import com.sun.javafx.css.converters.PaintConverter;

class CssProperties {

    static class HighlightFillProperty extends StyleableObjectProperty<Paint> {
        private final Object bean;

        private final CssMetaData<? extends Styleable, Paint> cssMetaData;

        public HighlightFillProperty(Object bean, Paint initialValue) {
            super(initialValue);
            this.bean = bean;
            cssMetaData = new PropertyCssMetaData<Styleable, Paint>(
                    this, "-fx-highlight-fill", PaintConverter.getInstance(),
                    initialValue);
        }

        @Override
        public Object getBean() {
            return bean;
        }

        @Override
        public String getName() {
            return "highlightFill";
        }

        @Override
        public CssMetaData<? extends Styleable, Paint> getCssMetaData() {
            return cssMetaData;
        }
    };

    static class HighlightTextFillProperty extends StyleableObjectProperty<Paint> {
        private final Object bean;

        private final CssMetaData<? extends Styleable, Paint> cssMetaData;

        public HighlightTextFillProperty(Object bean, Paint initialValue) {
            super(initialValue);
            this.bean = bean;
            cssMetaData = new PropertyCssMetaData<Styleable, Paint>(
                    this, "-fx-highlight-text-fill",
                    PaintConverter.getInstance(), initialValue);
        }

        @Override
        public Object getBean() {
            return bean;
        }

        @Override
        public String getName() {
            return "highlightTextFill";
        }

        @Override
        public CssMetaData<? extends Styleable, Paint> getCssMetaData() {
            return cssMetaData;
        }
    }
}
