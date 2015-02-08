package edu.millersville.umlatron;

import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

//Class created by Matt
public class UMLLine extends Line
{
	
	private double initX;
    private double initY;
	
	public UMLLine(double x1, double y1, double x2, double y2)
	{
		super(x1,y1,x2,y2);
		setCursor(Cursor.OPEN_HAND);
		
		setOnMouseDragged((event) -> {
            double dragX = event.getSceneX();
            double dragY = event.getSceneY();
            
            //calculate new position of the line
            double newXPosition1 = this.getStartX() - (initX - dragX);
            double newXPosition2 = this.getEndX() - (initX - dragX);
            double newYPosition1 = this.getStartY() - (initY - dragY);
            double newYPosition2 = this.getEndY() - (initY - dragY);
            
            //Makes sure you don't drag the line off the screen
            if ((newXPosition1 >= 0) && (newXPosition2 >= 0) && (newXPosition1 <= 750) && (newXPosition2 <= 750))
            {
            	this.setStartX(newXPosition1);
            	this.setEndX(newXPosition2);
            	initX = dragX;
            }
            if ((newYPosition1 >= 0) && (newYPosition2 >= 0) && (newYPosition1 <= 750) && (newYPosition2 <= 750))
            {
            	this.setStartY(newYPosition1);
            	this.setEndY(newYPosition2);
            	initY = dragY;
            }
        });
		
		setOnMousePressed((event) -> 
		{
            //when mouse is pressed, store initial position.
            initX = event.getSceneX();
            initY = event.getSceneY();
        });		
	}
}
