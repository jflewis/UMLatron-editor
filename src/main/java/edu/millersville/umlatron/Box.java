/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.millersville.umlatron;

import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author John Lewis
 */
public class Box extends Rectangle{
    
    private double initX;
    private double initY;
    private Point2D dragAnchor;
    
    public Box(Color color, double x, double y){
        super(70, 100, color);
        setTranslateX(x);
        setTranslateY(y);
        setArcWidth(20);
        setArcHeight(20);
        setCursor(Cursor.OPEN_HAND);

        setOnMouseDragged((event) -> {
            double dragX = event.getSceneX() - dragAnchor.getX();
            double dragY = event.getSceneY() - dragAnchor.getY();
            //calculate new position of the circle
            double newXPosition = initX + dragX;
            double newYPosition = initY + dragY;
            //if new position do not exceeds borders of the rectangle, translate to this position
            if ((newXPosition >= getX()) && (newXPosition <= 750 - ((getX() + widthProperty().getValue())))) {
                setTranslateX(newXPosition);
            }
            if ((newYPosition >= getY()) && (newYPosition <= 750 - (getY() + heightProperty().getValue()))) {
                setTranslateY(newYPosition);
            }
        });

        setOnMousePressed((event) -> {
            //when mouse is pressed, store initial position
            initX = getTranslateX();
            initY = getTranslateY();
            dragAnchor = new Point2D(event.getSceneX(), event.getSceneY());

        });
    }
}
