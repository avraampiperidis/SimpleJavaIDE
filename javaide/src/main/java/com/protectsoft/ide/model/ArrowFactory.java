package com.protectsoft.ide.model;


import java.util.function.IntFunction;

import org.reactfx.value.Val;

import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

 public class ArrowFactory implements IntFunction<Node> {
	 
    private final ObservableValue<Integer> shownLine;

    public ArrowFactory(ObservableValue<Integer> shownLine) {
        this.shownLine = shownLine;
    }
    

    public Node apply(int lineNumber) {
        Polygon triangle = new Polygon(0.0, 0.0, 10.0, 5.0, 0.0, 10.0);
        triangle.setFill(Color.GREEN);

        ObservableValue<Boolean> visible = Val.map(
                shownLine,
                sl -> sl == lineNumber);

        triangle.visibleProperty().bind(
                Val.flatMap(triangle.sceneProperty(), scene -> {
                    return scene != null ? visible : Val.constant(false);
                }));

        return triangle;
    }
    
    
}