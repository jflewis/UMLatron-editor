package edu.millersville.umlatron.view;

import edu.millersville.umlatron.Util.AnchorInfo;
import java.util.ArrayList;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

/**
 *
 * @authors Matthew Hipszer , John Lewis
 *
 */
public class UMLLine extends Group {

    private static int lineCount;
    protected int id;
    protected boolean dashed = false;

    ChangeListener<Number> listener = (ObservableValue<? extends Number> ov, Number old_state, Number new_state) -> {

        calculateAnchorPoints();

    };

    protected Line line = new Line();
    ClassBox startNode, endNode;
    DoubleBinding deltaX, deltaY, distance;

    ArrayList<AnchorInfo> startPoints = new ArrayList<>();
    ArrayList<AnchorInfo> endPoints = new ArrayList<>();

    /**
     *
     * @param startNode The AnchorPoint that the starting point of the line is
     * attached to.
     * @param endNode The AnchorPoint that the ending point of the line is
     * attached to.
     */
    public UMLLine(ClassBox startNode, ClassBox endNode) {
        super();

        //
        this.id = lineCount;
        lineCount++;
        //
        
        this.startNode = startNode;
        this.endNode = endNode;
        startNode.addLine(this);
        endNode.addLine(this);

        startPoints.add(startNode.getNorthPoint());
        startPoints.add(startNode.getSouthPoint());
        startPoints.add(startNode.getWestPoint());
        startPoints.add(startNode.getEastPoint());

        endPoints.add(endNode.getNorthPoint());
        endPoints.add(endNode.getSouthPoint());
        endPoints.add(endNode.getWestPoint());
        endPoints.add(endNode.getEastPoint());

        deltaX = line.endXProperty().subtract(line.startXProperty());
        deltaY = line.startYProperty().subtract(line.endYProperty());
        distance = deltaX.add(deltaY);

        distance.addListener(listener);

        line.setCursor(Cursor.OPEN_HAND);
        line.setStrokeWidth(2.0);
        calculateAnchorPoints();
        this.getChildren().add(line);

    }

    /**
     *
     */
    protected void setDashed() {
        if (dashed == false) {
            line.getStrokeDashArray().addAll(9d, 9d, 9d, 9d);
            dashed = true;
        }

    }

    protected void setSolid() {
        line.getStrokeDashArray().clear();
        dashed = false;

    }

    public int getIntId() {
        return id;
    }

    /**
     * Computes the slope (in both direction for each end point), will adjust
     * the anchor point with respect to that angle
     */
    final void calculateAnchorPoints() {
        
        double min = 999999999;
        AnchorInfo startAnchor = startPoints.get(0);
        AnchorInfo endAnchor = endPoints.get(0);
        double distance = 0;

        for (AnchorInfo start : startPoints) {
            for (AnchorInfo end : endPoints) {
                distance = Math.hypot(start.getX().get() - end.getX().get(), start.getY().get() - end.getY().get());
                if (distance < min) {
                    min = distance;
                    startAnchor = start;
                    endAnchor = end;
                }
            }

        }
        line.startXProperty().unbind();
        line.startYProperty().unbind();
        line.endXProperty().unbind();
        line.endYProperty().unbind();

        line.startXProperty().bind(startAnchor.getX());
        line.startYProperty().bind(startAnchor.getY());

        line.endXProperty().bind(endAnchor.getX());
        line.endYProperty().bind(endAnchor.getY());

    }

    /**
     *
     */
    public void updateHead() {

    }

    public void destroy() {
        startNode.removeLine(this);
        endNode.removeLine(this);
        Pane pane = (Pane) this.getParent();
        pane.getChildren().remove(this);
    }
 

    /**
     *
     */
    public void toggleDashed() {
        if (line.getStrokeDashArray().isEmpty()) {
            line.getStrokeDashArray().addAll(5d, 5d, 5d, 5d);
        } else {
            line.getStrokeDashArray().clear();
        }
    }
    
   

}
