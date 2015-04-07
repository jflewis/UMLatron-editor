package edu.millersville.umlatron.view;

import javafx.scene.Cursor;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Line;

/**
 *
 * @author Matthew Hipszer
 *
 */
public class UMLLine extends Group {

    private double initX;
    private double initY;
    protected Point2D startingAnchor;
    protected Point2D endingAnchor;
    protected int point1Int;
    protected int point2Int;
    private Point2D dragAnchor1;
    private Point2D dragAnchor2;
    protected AnchorPoint anchorPoint1;
    protected AnchorPoint anchorPoint2;
    private static int lineCount;
    protected int id;
    protected boolean dashed = false;
    
    protected Line line;


    /**
     *
     * @param a1 The AnchorPoint that the starting point of the line is attached
     * to.
     * @param a2 The AnchorPoint that the ending point of the line is attached
     * to.
     */
    public UMLLine(AnchorPoint a1, AnchorPoint a2) {
        super();
        
        line = new Line(a1.getAnchorPoint(0).getX(), a1.getAnchorPoint(0).getY(), a2
                .getAnchorPoint(0).getX(), a2.getAnchorPoint(0).getY());
        
        id = lineCount;
        ++lineCount;

        anchorPoint1 = a1;
        anchorPoint2 = a2;

        // sets the anchorPoints
        updateAnchorPoints();

        line.setCursor(Cursor.OPEN_HAND);
        line.setStrokeWidth(2.0);

      
        MenuItem delete = new MenuItem("delete");

        delete.setOnAction((event) -> {
            deleteSelf();
        });

        ContextMenu contextMenu = new ContextMenu(delete);

        setOnMouseClicked((event) -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                System.out.println("hello");
                contextMenu.show(this, event.getScreenX(), event.getScreenY());
            } else {
                contextMenu.hide();
            }
        });
        
        this.getChildren().add(line);
    }

    /**
     *
     */
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

    }
    
      protected void setDashed() {
        if(dashed == false){
            line.getStrokeDashArray().addAll(9d, 9d, 9d, 9d);
            dashed = true;
        }

    }

    protected void setSolid() {
        line.getStrokeDashArray().clear();
        dashed = false;

    }

    /**
     *
     * @return returns which anchor point the starting point of the line is
     * connected to.
     */
    public int getAnchorPoint1Int() {
        return point1Int;
    }

    /**
     *
     * @return returns which anchor point the ending point of the line is
     * connected to.
     */
    public int getAnchorPoint2Int() {
        return point2Int;
    }

 
    public void deleteSelf() {
        anchorPoint1.deleteLine(id);
        anchorPoint2.deleteLine(id);
    }

    public int getIntId() {
        return id;
    }
    
    public void setStartX(double d){
        line.setStartX(d);
    }
    public void setStartY(double d){
        line.setStartY(d);   
    }
    public void setEndX(double d){
        line.setEndX(d);
    }
    public void setEndY(double d){
        line.setEndY(d);
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
