/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.millersville.umlatron.view;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * 
 * @author John Lewis
 */
public class Box extends Rectangle{
    
    private double initX;
    private double initY;
    private int side = 50;
    private int anchorCount;
    private Point2D[] anchorPoints;
    private Point2D dragAnchor;
    private ArrayList<String> pointTypes;
    private ArrayList<UMLLine> lines;
    
    
    public Box(Color color, double x, double y){
        super(50, 50, color);
        pointTypes = new ArrayList<String>();
        lines = new ArrayList<UMLLine>();
        setTranslateX(x);
        setTranslateY(y);
        setArcWidth(20);
        setArcHeight(20);
        anchorCount = 4;
        anchorPoints = new Point2D[anchorCount];
        setAnchorPoints(x, y);
        
        setCursor(Cursor.OPEN_HAND);
        
        setOnMouseDragged((event) -> {
            double dragX = event.getSceneX() - dragAnchor.getX();
            double dragY = event.getSceneY() - dragAnchor.getY();
            //calculate new position of the circle
            double newXPosition = initX + dragX;
            double newYPosition = initY + dragY;
            //if new position do not exceeds borders of the rectangle, translate to this position
            if ((newXPosition >= getX()) && (newXPosition <= this.sceneProperty().get().getWidth() - ((getX() + widthProperty().getValue())))) {
                setTranslateX(newXPosition);
                setAnchorPoints(newXPosition, newYPosition);
                
                for (int i = 0; i < lines.size(); ++i)
                {
                	lines.get(i).updateAnchorPoints();
                	if (pointTypes.get(i).equals("start"))
                	{
                		lines.get(i).setStartX(anchorPoints[lines.get(i).getAnchorPoint1Int()].getX());
                	}
                	if (pointTypes.get(i).equals("end"))
                	{
                		lines.get(i).setEndX(anchorPoints[lines.get(i).getAnchorPoint2Int()].getX());
                	}
                }
            }
            if ((newYPosition >= getY()) && (newYPosition <= this.sceneProperty().get().getHeight() - (getY() + heightProperty().getValue()))) {
                setTranslateY(newYPosition);
                setAnchorPoints(newXPosition, newYPosition);
                for (int i = 0; i < lines.size(); ++i)
                {
                	if (pointTypes.get(i).equals("start"))
                	{
                		lines.get(i).setStartY(anchorPoints[lines.get(i).getAnchorPoint1Int()].getY());
                	}
                	if (pointTypes.get(i).equals("end"))
                	{
                		lines.get(i).setEndY(anchorPoints[lines.get(i).getAnchorPoint2Int()].getY());
                	}
                }
            }
            event.consume();
        });

        setOnMousePressed((event) -> {
            //when mouse is pressed, store initial position
            initX = getTranslateX();
            initY = getTranslateY();
            dragAnchor = new Point2D(event.getSceneX(), event.getSceneY());
            event.consume();
        });
        
        MenuItem delete = new MenuItem("delete");
        delete.setOnAction(event ->{
            Pane pane = (Pane)this.getParent();
            pane.getChildren().remove(Box.this);
        });
        
        ContextMenu contextMenu = new ContextMenu(delete);

        setOnMouseClicked(event -> {
        	//I need to be able to get the state here to check if it is line.
            if(event.getButton() == MouseButton.SECONDARY){
                System.out.println("hello");
                contextMenu.show(this, event.getScreenX(), event.getScreenY());
            }else{
                contextMenu.hide();
            }
            event.consume();
        });
        
        setOnMouseReleased(event -> {event.consume();});
    }
    
    /**
     * 
     * @param x The x position of the box after moving
     * @param y The y position of the box after moving
     */
    private void setAnchorPoints(double x, double y) 
    {
    	anchorPoints[0] = new Point2D(x, y + (side / 2)); //left
        anchorPoints[1] = new Point2D(x + (side / 2), y);  //top
        anchorPoints[2] = new Point2D(x + side, y + (side / 2)); //right
        anchorPoints[3] = new Point2D(x + (side / 2), y + side); //bottom	
	}

    /**
     * 
     * @param i The index of the anchor point
     * @return Returns index i of anchorPoints
     */
	public Point2D getAnchorPoint(int i)
    {
    	return anchorPoints[i];
    }
    
	/**
	 * 
	 * @return Returns how many anchor points this box has
	 */
    public int getAnchorCount()
    {
    	return anchorCount;
    }
    
    /**
     * 
     * @param d The value that initX will be set to.
     */
    public void setInitX(double d)
    {
    	initX = d;
    }
    
    /**
     * 
     * @param d The value that initY will be set to.
     */
    public void setInitY(double d)
    {
    	initY = d;
    }
    
    /**
     * 
     * @return Returns initX.
     */
    public double getInitX()
    {
    	return initX;
    }
    
    /**
     * 
     * @return Returns initY.
     */
    public double getInitY()
    {
    	return initY;
    }

    /**
     * 
     * @param str The String that signifies whether this lineType is a startPoint or endPoint.
     */
	public void addLineType(String str) 
	{
		pointTypes.add(str);	
	}

	/**
	 * 
	 * @param line Adds a UMLLine line to Lines.
	 */
	public void addLine(UMLLine line) 
	{
		lines.add(line);	
	}
}

