package edu.millersville.umlatron.view;


import javafx.scene.Cursor;
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
public class UMLLine extends Line 
{

    private double initX;
    private double initY;
    private boolean startingBoxContains = false;
    private boolean endingBoxContains = false;
    private double side = 5;

    /**
     * Creates a line.
     * @param x1			x value of starting point.
     * @param y1			y value of starting point.
     * @param x2			x value of ending point.
     * @param y2			y value of ending point.
     */
    public UMLLine(double x1, double y1, double x2, double y2) 
    {
        super(x1, y1, x2, y2);
        setCursor(Cursor.OPEN_HAND);
        setStrokeWidth(2.0);

        setOnMousePressed((event) -> 
        {
            initX = event.getSceneX();
            initY = event.getSceneY();
            startingBoxContains(initX, initY);
            endingBoxContains(initX, initY);
            event.consume();
        });

        setOnMouseDragged((event) -> 
        {
            if (startingBoxContains) 
            {
            	this.setStartX(getPlacement(event.getSceneX(), 0.0, this.sceneProperty().get().getWidth()));
            	this.setStartY(getPlacement(event.getSceneY(), 0.0, this.sceneProperty().get().getHeight()));
            } 
            else if (endingBoxContains) 
            {
            	this.setEndX(getPlacement(event.getSceneX(), 0.0, this.sceneProperty().get().getWidth()));
            	this.setEndY(getPlacement(event.getSceneY(), 0.0, this.sceneProperty().get().getHeight()));
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
                if ((newXPosition1 >= 0) && (newXPosition2 >= 0) && (newXPosition1 <= this.sceneProperty().get().getWidth()) && (newXPosition2 <= this.sceneProperty().get().getWidth())) {
                    this.setStartX(newXPosition1);
                    this.setEndX(newXPosition2);
                    initX = dragX;
                }
                if ((newYPosition1 >= 0) && (newYPosition2 >= 0) && (newYPosition1 <= 750) && (newYPosition2 <= 750)) {
                    this.setStartY(newYPosition1);
                    this.setEndY(newYPosition2);
                    initY = dragY;
                }
            }
            event.consume();
        });

        setOnMouseReleased((event) -> 
        {
            startingBoxContains = false;
            endingBoxContains = false;
            event.consume();
        });

        MenuItem delete = new MenuItem("delete");

        delete.setOnAction((event) ->
        {
            Group group = (Group)this.getParent();
            group.getChildren().remove(this);
        });

        ContextMenu contextMenu = new ContextMenu(delete);


        setOnMouseClicked((event) -> 
        {
            if(event.getButton() == MouseButton.SECONDARY)
            {
                System.out.println("hello");
                contextMenu.show(this, event.getScreenX(), event.getScreenY());
            }
            else
            {
                contextMenu.hide();
            }
        });
    }

    
    /**
     * Gets where to place the value based on an lower and upper bound.
     * @param value			The value to place.
     * @param lowerBound	The lower bound.
     * @param upperBound	The upper bound.
     * @return				Returns the position to place value.
     */
    private double getPlacement(double value, double lowerBound, double upperBound)
    {
    	if (value < lowerBound)
    	{
    		return lowerBound;
    	}
    	if (value > upperBound)
    	{
    		return upperBound;
    	}
    	return value;
    }
    
    
    /**
     * Checks if the clicked point on the line is within a certain range of the line's starting point.
     * @param x				The x value of the mouse.
     * @param y				The y value of the mouse.
     */
	private void startingBoxContains(double x, double y) 
	{
        if (x < this.getStartX() - side || x > this.getStartX() + side || y < this.getStartY() - side || y > this.getStartY() + side) 
        {
            startingBoxContains = false;
        } 
        else 
        {
            startingBoxContains = true;
        }
    }

	
	/**
	 * Checks if the clicked point on the line is within a certain range of the line's ending point.
	 * @param x				The x value of the mouse.
	 * @param y				The y value of the mouse.
	 */
    private void endingBoxContains(double x, double y) 
    {
        if (x < this.getEndX() - side || x > this.getEndX() + side || y < this.getEndY() - side || y > this.getEndY() + side) 
        {
            endingBoxContains = false;
        } 
        else 
        {
            endingBoxContains = true;
        }
    }
}
