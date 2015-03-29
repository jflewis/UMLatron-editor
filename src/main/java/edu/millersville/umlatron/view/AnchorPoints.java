package edu.millersville.umlatron.view;

import javafx.geometry.Point2D;

public interface AnchorPoints 
{    
    /**
     * 
     * @param x The x position to be used to calculate the x position of the anchor points.
     * @param y The y position to be used to calculate the y position of the anchor points.
     */
    void setAnchorPoints(double x, double y);
    
    /**
     * 
     * @param x The x position that will be used to update the x position of the anchor points.
     */
    void updateXAnchorPoints(double x);
    
    /**
     * 
     * @param y The y position to be used to calculate the y position of the anchor points.
     */
    void updateYAnchorPoints(double y);
    
    /**
     * 
     * @param i The index of the anchor point
     * @return Returns index i of anchorPoints
     */
	Point2D getAnchorPoint(int i);
    
	/**
	 * 
	 * @return Returns how many anchor points this box has
	 */
    int getAnchorCount();
    
    /**
     * 
     * @param str The String that signifies whether this lineType is a startPoint or endPoint.
     */
	public void addLineType(String str);

	/**
	 * 
	 * @param line Adds a UMLLine line to Lines.
	 */
	public void addLine(UMLLine line);
	
	/**
     * 
     * @param d The value that initX will be set to.
     */
    public void setInitX(double d);
	
	/**
     * 
     * @param d The value that initY will be set to.
     */
    public void setInitY(double d);
    
    /**
     * 
     * @return Returns initX.
     */
    public double getInitX();
    
    /**
     * 
     * @return Returns initY.
     */
    public double getInitY();
}
