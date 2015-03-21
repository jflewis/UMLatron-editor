/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.millersville.umlatron.view;

import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 * Class representing a line with a open diamond on the end of it
 *
 * @author John
 */
public class DiamondLine extends Path {

    private MoveTo moveTo;
    private double currentX = 0;
    private double currentY = 0;
    private double initX;
    private double initY;
    private boolean moveWhole = false;
    private double lineLength = 70;

    /**
     * Draws a diamond line using path elements
     *
     * @param x
     * @param y
     */
    public DiamondLine(double x, double y) {
        super();
        setCursor(Cursor.OPEN_HAND);

        //creating path elements
        //==========================================
        currentX = x;
        currentY = y;
        setStrokeWidth(2.0);

        moveTo = new MoveTo();
        moveTo.setX(x);
        moveTo.setY(y);

        currentY = currentY - lineLength;
        LineTo moveUp = new LineTo(currentX, currentY);

        currentY = currentY - 10;
        currentX = currentX + 10;
        LineTo d1 = new LineTo(currentX, currentY);

        currentY = currentY - 10;
        currentX = currentX - 10;
        LineTo d2 = new LineTo(currentX, currentY);

        currentY = currentY + 10;
        currentX = currentX - 10;
        LineTo d3 = new LineTo(currentX, currentY);

        currentY = currentY + 10;
        currentX = currentX + 10;
        LineTo d4 = new LineTo(currentX, currentY);

        getElements().addAll(moveTo, moveUp, d1, d2, d3, d4);
        setFill(Color.BLACK);

        //setting action events
        //=================================================
        
        setOnMousePressed((event) -> {
            //when mouse is pressed, store initial position for delta
            initX = event.getSceneX();
            initY = event.getSceneY();
            moveWholeShape(event.getSceneX(), event.getSceneY());
            //System.out.println("mouse is at : " + event.getSceneX() + ", " + event.getSceneY());
            //System.out.println("moveTo is at : " + moveTo.getX() + ", " + moveTo.getY());
            event.consume();
        });

        setOnMouseDragged((event) -> {
            //calculate new position of the circle
            double deltaX = (initX - event.getSceneX());
            double deltaY = (initY - event.getSceneY());

            if (!moveWhole) {

                moveTo.setX(moveTo.getX() - deltaX);
                moveTo.setY(moveTo.getY() - deltaY);

                currentY = (moveTo.getY() - deltaY) - lineLength;
                moveUp.setX(moveUp.getX() - (initX - event.getSceneX()));
                moveUp.setY(currentY);

                currentY = currentY - 10;
                currentX = currentX + 10;
                d1.setX(d1.getX() - deltaX);
                d1.setY(currentY - deltaY);

                currentY = d1.getY() - 10;
                currentX = d1.getX() - 10;
                d2.setX(currentX);
                d2.setY(currentY);

                currentY = currentY + 10;
                currentX = currentX - 10;
                d3.setX(currentX);
                d3.setY(currentY);

                currentY = currentY + 10;
                currentX = currentX + 10;
                d4.setX(currentX);
                d4.setY(currentY);

                initX = event.getSceneX();
                initY = event.getSceneY();
            } else {
                moveTo.setX(event.getSceneX());
                moveTo.setY(event.getSceneY());
                lineLength = Math.abs(moveTo.getY() - moveUp.getY());
            }

            event.consume();
        });

        setOnMouseReleased(event -> {
            moveWhole = false;
            event.consume();
        });
        
        MenuItem delete = new MenuItem("delete");
        delete.setOnAction(event ->{
            Group group = (Group)this.getParent();
            group.getChildren().remove(this);
        });
        
        ContextMenu contextMenu = new ContextMenu(delete);

        
        setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.SECONDARY){
                System.out.println("hello");
                contextMenu.show(this, event.getScreenX(), event.getScreenY());
            }else{
                contextMenu.hide();
            }
            event.consume();
        });
        
      

    }

    /**
     * checks if the mouse was pressed in the area responsible for increasing the line length
     * @param x
     * @param y 
     */
    private void moveWholeShape(double x, double y) {
        int size = 30;
        moveWhole = (moveTo.getX() - size < x && x < moveTo.getX() + size) && (moveTo.getY() > y && y > moveTo.getY() - size);
        System.out.println(moveWhole);

    }

}
