package edu.millersville.umlatron.view;



import javafx.scene.Cursor;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;

/**
 * 
 * @author Matthew Hipszer
 *
 */
public class UMLLine extends Line 
{
    private double initX;
    private double initY;
    private Point2D startingAnchor;
    private Point2D endingAnchor;
    private int point1Int;
    private int point2Int;
    private Point2D dragAnchor1;
    private Point2D dragAnchor2;
    private Box box1;
    private Box box2;

    /**
     * 
     * @param b1 The box that the starting point of the line is attached to.
     * @param b2 The box that the ending point of the line is attached to.
     */
    public UMLLine(Box b1, Box b2) 
    {
        super(b1.getAnchorPoint(3).getX(), b1.getAnchorPoint(3).getY(), b2.getAnchorPoint(3).getX(), b2.getAnchorPoint(3).getY());
        
        box1 = b1;
        box2 = b2;
        
        //sets the anchorPoints
        updateAnchorPoints();
               
        setCursor(Cursor.OPEN_HAND);
        setStrokeWidth(2.0);

        setOnMousePressed((event) -> 
        {
            initX = event.getSceneX();
            initY = event.getSceneY();
            box1.setInitX(box1.getTranslateX());
            box1.setInitY(box1.getTranslateX());
            dragAnchor1 = new Point2D(event.getSceneX(), event.getSceneY());
            box2.setInitX(box2.getTranslateX());
            box2.setInitY(box2.getTranslateX());
            dragAnchor2 = new Point2D(event.getSceneX(), event.getSceneY());
            event.consume();
        });

        setOnMouseDragged((event) -> 
        {
        	double dragXBox1 = event.getSceneX() - dragAnchor1.getX();
            double dragYBox1 = event.getSceneY() - dragAnchor1.getY();
            double dragXBox2 = event.getSceneX() - dragAnchor2.getX();
            double dragYBox2 = event.getSceneY() - dragAnchor2.getY();
            
            double newXPositionB1 = box1.getInitX() + dragXBox1;
            double newYPositionB1 = box1.getInitY() + dragYBox1;
            double newXPositionB2 = box2.getInitX() + dragXBox2;
            double newYPositionB2 = box2.getInitY() + dragYBox2;

            double dragX = event.getSceneX();
            double dragY = event.getSceneY();

            //calculate new position of the line
            double newXPosition1 = this.getStartX() - (initX - dragX);
            double newXPosition2 = this.getEndX() - (initX - dragX);
            double newYPosition1 = this.getStartY() - (initY - dragY);
            double newYPosition2 = this.getEndY() - (initY - dragY);

            //Makes sure you don't drag the line off the screen
            if ((newXPosition1 >= 0) && (newXPosition2 >= 0) 
             && (newXPosition1 <= this.sceneProperty().get().getWidth()) && (newXPosition2 <= this.sceneProperty().get().getWidth())
             && (newXPositionB1 >= box1.getX())  && (newXPositionB1 <= box1.sceneProperty().get().getWidth() - ((box1.getX() + box1.widthProperty().getValue())))
             && (newXPositionB2 >= box2.getX())  && (newXPositionB2 <= box2.sceneProperty().get().getWidth() - ((box2.getX() + box2.widthProperty().getValue()))))
            {
                this.setStartX(newXPosition1);
                this.setEndX(newXPosition2);
                box1.setTranslateX(newXPositionB1);
                box2.setTranslateX(newXPositionB2);
                initX = dragX;
            }
            if ((newYPosition1 >= 0) && (newYPosition2 >= 0) 
             && (newYPosition1 <= 750) && (newYPosition2 <= 750)
             &&  (newYPositionB1 >= box1.getY()) && (newYPositionB1 <= box1.sceneProperty().get().getHeight() - (box1.getY() + box1.heightProperty().getValue()))  
             &&  (newYPositionB2 >= box2.getY()) && (newYPositionB2 <= box2.sceneProperty().get().getHeight() - (box2.getY() + box2.heightProperty().getValue()))) 
            {
                this.setStartY(newYPosition1);
                this.setEndY(newYPosition2);
                box1.setTranslateY(newYPositionB1);
                box2.setTranslateY(newYPositionB2);
                initY = dragY;
            }        
            event.consume();
        });

        setOnMouseReleased((event) -> 
        {
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
     * 
     */
    public void updateAnchorPoints() 
    {
    	double min = 999999999;
        point1Int = 0;
        point2Int = 0;
        for (int i = 0; i < box1.getAnchorCount(); ++i)
        {
            startingAnchor = new Point2D(box1.getAnchorPoint(i).getX(), box1.getAnchorPoint(i).getY());
        	for (int j = 0; j < box2.getAnchorCount(); ++j)
        	{
        		endingAnchor = new Point2D(box2.getAnchorPoint(j).getX(), box2.getAnchorPoint(j).getY());
        		if (startingAnchor.distance(endingAnchor) < min)
        		{
        			min = startingAnchor.distance(endingAnchor);
        			point1Int = i;
        			point2Int = j;
        		}
        	}
        }
        this.setStartX(box1.getAnchorPoint(point1Int).getX());
        this.setStartY(box1.getAnchorPoint(point1Int).getY());
        this.setEndX(box2.getAnchorPoint(point2Int).getX());
        this.setEndY(box2.getAnchorPoint(point2Int).getY());
		
	}
    
    /**
     * 
     * @return returns which anchor point the starting point of the line is connected to.
     */
    public int getAnchorPoint1Int()
    {
    	return point1Int;
    }
    
    /**
     * 
     * @return returns which anchor point the ending point of the line is connected to.
     */
    public int getAnchorPoint2Int()
    {
    	return point2Int;
    }
}

