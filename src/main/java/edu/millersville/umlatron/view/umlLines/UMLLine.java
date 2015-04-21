package edu.millersville.umlatron.view.umlLines;

import edu.millersville.umlatron.Util.AnchorInfo;
import edu.millersville.umlatron.view.ClassBox;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @authors Matthew Hipszer , John Lewis
 *
 */
public class UMLLine extends Group implements java.io.Externalizable {

    private static int lineCount;
    protected int id;
    protected boolean dashed = false;
    private DropShadow borderGlow;
    private DropShadow noGlow;
    ChangeListener<Number> listener = (ObservableValue<? extends Number> ov, Number old_state, Number new_state) -> {

        calculateAnchorPoints();

    };
    protected Line line = new Line();
    ClassBox startNode, endNode;
    DoubleBinding deltaX, deltaY, distance;
    ArrayList<AnchorInfo> startPoints = new ArrayList<>();
    ArrayList<AnchorInfo> endPoints = new ArrayList<>();
    
    /**
	 * A default constructor used for loading
	 */
    public UMLLine(){
        
    }

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
        
        borderGlow = new DropShadow();
    	borderGlow.setColor(Color.BLUE);
    	borderGlow.setOffsetX(0f);
    	borderGlow.setOffsetY(0f);
    	borderGlow.setWidth(20);
    	borderGlow.setHeight(20);
    	//remove highlighting
    	noGlow = new DropShadow();
    	noGlow.setColor(Color.BLUE);
    	noGlow.setOffsetX(0f);
    	noGlow.setOffsetY(0f);
    	noGlow.setWidth(0);
    	noGlow.setHeight(0);

    }
    
    /**
	 * 
	 * @return Returns the UMLLine created by the method.
	 */
    public UMLLine createLineFromLoad(){
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
        return this;
    }
   

    /**
	 * Turns the line to a dashed line.
	 */
    protected void setDashed() {
        if (dashed == false) {
            line.getStrokeDashArray().addAll(9d, 9d, 9d, 9d);
            dashed = true;
        }

    }

    /**
	 * Turns the line to a solid line.
	 */
    protected void setSolid() {
        line.getStrokeDashArray().clear();
        dashed = false;

    }

    /**
     * 
     * @return Returns the unique id of the line.
     */
    public int getIntId() {
        return id;
    }

    /**
     * Computes the slope (in both direction for each end point), will adjust
     * the anchor point with respect to that angle
     */
    public final void calculateAnchorPoints() {
        
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
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(startNode);
        out.writeObject(endNode);
        out.writeBoolean(dashed);
        
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        startNode  = (ClassBox)in.readObject();
        endNode = (ClassBox)in.readObject();
        dashed = in.readBoolean();
    }
    public void applySelection(){
    	this.setEffect(borderGlow);
    }
    public void removeSelection(){
    	this.setEffect(noGlow);
    }
}
