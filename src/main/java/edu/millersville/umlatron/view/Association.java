package edu.millersville.umlatron.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;

/**
 *
 * @author Matthew Hipszer
 *
 */
public class Association extends UMLLine implements SelectedPanel {

    Rotate rotate = new Rotate();
    Polygon polygon = new Polygon();
    Boolean filled = false;

    /**
     *
     * @param a1 The AnchorPoint that the starting point of the line is attached
     * to.
     * @param a2 The AnchorPoint that the ending point of the line is attached
     * to.
     */
    public Association(AnchorPoint a1, AnchorPoint a2) {
        super(a1, a2);
        polygon.getPoints().addAll(new Double[]{
            0.0, 0.0,
            -12.0, -7.0,
            -24.0, 0.0,
            -12.0,7.0
            
        });
        
        polygon.setFill(Color.WHITE);
        polygon.setStrokeWidth(1);
        polygon.setStroke(Color.BLACK);
        double deltaX = this.getStartY() - this.getEndY();
        double deltaY = this.getEndX() - this.getStartX();
        double slopeInDegrees = Math.toDegrees(Math.atan2(deltaX,deltaY));
        
        polygon.getTransforms().add(rotate);
        rotate.setAngle(-slopeInDegrees);
        
        polygon.setTranslateX(this.getEndX());
        polygon.setTranslateY(this.getEndY());

        
        
    }
     @Override
    public void updateAnchorPoints() {
        double min = 999999999;
        point1Int = 0;
        point2Int = 0;
        for (int i = 0; i < anchorPoint1.getAnchorCount(); ++i) {
            startingAnchor = new Point2D(anchorPoint1.getAnchorPoint(i).getX(),
                    anchorPoint1.getAnchorPoint(i).getY());
            for (int j = 0; j < anchorPoint2.getAnchorCount(); ++j) {
                endingAnchor = new Point2D(anchorPoint2.getAnchorPoint(j)
                        .getX(), anchorPoint2.getAnchorPoint(j).getY());
                if (startingAnchor.distance(endingAnchor) < min) {
                    min = startingAnchor.distance(endingAnchor);
                    point1Int = i;
                    point2Int = j;
                }
            }
        }
        this.setStartX(anchorPoint1.getAnchorPoint(point1Int).getX());
        this.setStartY(anchorPoint1.getAnchorPoint(point1Int).getY());
        this.setEndX(anchorPoint2.getAnchorPoint(point2Int).getX());
        this.setEndY(anchorPoint2.getAnchorPoint(point2Int).getY());
        
        double deltaX = this.getStartY() - this.getEndY();
        double deltaY = this.getEndX() - this.getStartX();
        //gets you the degress of the x axis(on the left) of the second clicked node
        double slopeInDegrees = Math.toDegrees(Math.atan2(deltaX,deltaY));
        
        if (polygon != null){
            polygon.setTranslateX(this.getEndX());
            polygon.setTranslateY(this.getEndY());
            rotate.setAngle(-slopeInDegrees);

        }
        
        
    }
    
    
    public Polygon diamond(){
        return polygon;
    }
    
    @Override
    public void deleteSelf(){
        Pane pane = (Pane)this.getParent();
        pane.getChildren().remove(polygon);
        anchorPoint1.deleteLine(id);
        anchorPoint2.deleteLine(id);  
    }
    
    private void setFillWhite(){
        if(polygon != null){
            filled = false;
            polygon.setFill(Color.WHITE);
            
        }
    }
    
    private void setFillBlack(){
        if(polygon != null){
            filled = true;
            polygon.setFill(Color.BLACK);
            
        }
    }
    
    /**
     * creates the currently selected panel for this Node
     * @param h the views HBox
     */
    @Override
    public void createAndGeneratePanel(HBox h) {

        h.getChildren().clear();
        DropShadow shadow = new DropShadow();

        Button aggregation = new Button("Aggregation");
        aggregation.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(aggregation, Priority.ALWAYS);
        aggregation.setOnAction((ActionEvent e) -> {
            setFillWhite();

        });
        aggregation.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        aggregation.setEffect(shadow);
                    }
                });
        aggregation.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        aggregation.setEffect(null);
                    }
                });

        Button comp = new Button("Composition");
        comp.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(comp, Priority.ALWAYS);
        comp.setOnAction((ActionEvent e) -> {
           setFillBlack();
        });
        comp.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        comp.setEffect(shadow);
                    }
                });
        comp.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        comp.setEffect(null);
                    }
                });
        
        Button deleteB = new Button("Delete");
        deleteB.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(deleteB, Priority.ALWAYS);
        deleteB.setOnAction((ActionEvent e) -> {
           deleteSelf();
           h.getChildren().clear();
        });
        deleteB.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        deleteB.setEffect(shadow);
                    }
                });
        deleteB.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        deleteB.setEffect(null);
                    }
                });
        
        Label label = new Label("Currently selected node : Association ");


        h.getChildren().addAll(label,aggregation, comp,deleteB);

    }
}
