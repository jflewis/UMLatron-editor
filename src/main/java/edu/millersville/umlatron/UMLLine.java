package edu.millersville.umlatron;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

//All dragging events work improperly if the mouse is moving too fast.
public class UMLLine extends Line
{
	
	private double initX;
    private double initY;
    private double side = 10;
    
	public UMLLine(double x1, double y1, double x2, double y2)
	{
		super(x1,y1,x2,y2);
		setCursor(Cursor.OPEN_HAND);
		
		setOnMouseDragged((event) -> {
			
			if (startingBoxContains(event.getSceneX(), event.getSceneY()))
			{
				this.setStartX(event.getSceneX());
				this.setStartY(event.getSceneY());
			}
			else if (endingBoxContains(event.getSceneX(), event.getSceneY()))
			{
				this.setEndX(event.getSceneX());
				this.setEndY(event.getSceneY());
			}
			else
			{
				double dragX = event.getSceneX();
				double dragY = event.getSceneY();
            
				//calculate new position of the line
				double newXPosition1 = this.getStartX() - (initX - dragX);
				double newXPosition2 = this.getEndX() - (initX - dragX);
				double newYPosition1 = this.getStartY() - (initY - dragY);
				double newYPosition2 = this.getEndY() - (initY - dragY);
            
				//Makes sure you don't drag the line off the screen
				if ((newXPosition1 >= 0) && (newXPosition2 >= 0) && (newXPosition1 <= this.sceneProperty().get().getWidth()) && (newXPosition2 <= this.sceneProperty().get().getWidth()))
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
			}
            event.consume();
        });
		
		setOnMousePressed((event) -> 
		{
            //when mouse is pressed, store initial position.
            initX = event.getSceneX();
            initY = event.getSceneY();
            event.consume();
        });		
	}

	private boolean startingBoxContains(double x, double y) 
	{
		if (x < this.getStartX() - (side/2) || x > this.getStartX() + (side/2) || y < this.getStartY() - (side/2) || y > this.getStartY() + (side/2))
		{
			return false;
		}
		return true;
	}

	private boolean endingBoxContains(double x, double y) 
	{
		if (x < this.getEndX() - (side/2) || x > this.getEndX() + (side/2) || y < this.getEndY() - (side/2) || y > this.getEndY() + (side/2))
		{	
			return false;
		}
		return true;
	}
	
	
}
