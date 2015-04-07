package edu.millersville.umlatron.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.transform.Rotate;

/**
 *
 * @author Matthew Hipszer
 *
 */
public class UMLArrowLine extends UMLLine implements SelectedPanel {

    private Line minSpreadLine;
    private Line maxSpreadLine;
    final Group group = new Group();
    Rotate rotate = new Rotate();


    /**
     *
     * @param a1 The AnchorPoint that the starting point of the line is attached
     * to.
     * @param a2 The AnchorPoint that the ending point of the line is attached
     * to.
     */
    public UMLArrowLine(AnchorPoint a1, AnchorPoint a2) {
        super(a1, a2);
  
        //slope of the line
        double deltaX = line.getStartY() - line.getEndY();
        double deltaY = line.getEndX() - line.getStartX();
        //gets you the degress of the x axis(on the left) of the second clicked node
        double slopeInDegrees = Math.toDegrees(Math.atan2(deltaX,deltaY));

        minSpreadLine = new Line(0,
                0, -10,
                -10);
        maxSpreadLine = new Line(0,
                0, -10,
                10);
        
        //add the arrow to a group
        group.getChildren().addAll(minSpreadLine,maxSpreadLine);
        group.getTransforms().add(rotate);
        rotate.setAngle(-slopeInDegrees);
        group.setTranslateX(line.getEndX());
        group.setTranslateY(line.getEndY());
        
        this.getChildren().add(group);

       
    }

    /**
     *
     * @return Returns the line 135 degrees clockwise to the main line.
     */
    public Node getLine1() {
        return minSpreadLine;
    }

    /**
     *
     * @return Returns the line 135 degrees counterclockwise to the main line.
     */
    public Node getLine2() {
        return maxSpreadLine;
    }
  
    
    @Override
    public void deleteSelf(){
        Pane pane = (Pane)this.getParent();
        pane.getChildren().remove(group);
        anchorPoint1.deleteLine(id);
        anchorPoint2.deleteLine(id);  
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
        line.setStartX(anchorPoint1.getAnchorPoint(point1Int).getX());
        line.setStartY(anchorPoint1.getAnchorPoint(point1Int).getY());
        line.setEndX(anchorPoint2.getAnchorPoint(point2Int).getX());
        line.setEndY(anchorPoint2.getAnchorPoint(point2Int).getY());
        
        double deltaX = line.getStartY() - line.getEndY();
        double deltaY = line.getEndX() - line.getStartX();
        //gets you the degress of the x axis(on the left) of the second clicked node
        double slopeInDegrees = Math.toDegrees(Math.atan2(deltaX,deltaY));
        
        if (group != null){
            group.setTranslateX(line.getEndX());
            group.setTranslateY(line.getEndY());
            rotate.setAngle(-slopeInDegrees);

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

        Button setDashed = new Button("Dashed");
        setDashed.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(setDashed, Priority.ALWAYS);
        setDashed.setOnAction((ActionEvent e) -> {
            setDashed();

        });
        setDashed.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        setDashed.setEffect(shadow);
                    }
                });
        setDashed.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        setDashed.setEffect(null);
                    }
                });

        Button setSolid = new Button("Solid");
        setSolid.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(setSolid, Priority.ALWAYS);
        setSolid.setOnAction((ActionEvent e) -> {
            setSolid();
        });
        setSolid.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        setSolid.setEffect(shadow);
                    }
                });
        setSolid.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        setSolid.setEffect(null);
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
        
        Label label = new Label("Currently selected node : Line ");


        h.getChildren().addAll(label,setDashed, setSolid,deleteB);

    }
}
