package edu.millersville.umlatron.view;

import edu.millersville.umlatron.model.LineType;
import javafx.geometry.Point2D;

public interface AnchorPoint {
	/**
	 * 
	 * @param x
	 *            The x position to be used to calculate the x position of the
	 *            anchor points.
	 * @param y
	 *            The y position to be used to calculate the y position of the
	 *            anchor points.
	 */
	void setAnchorPoints(double x, double y);

	/**
	 * 
	 * @param x
	 *            The x position that will be used to update the x position of
	 *            the anchor points.
	 */
	void updateXAnchorPoints(double x);

	/**
	 * 
	 * @param y
	 *            The y position to be used to calculate the y position of the
	 *            anchor points.
	 */
	void updateYAnchorPoints(double y);

	/**
	 * 
	 * @param i
	 *            The index of the anchor point
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
	 * @param str
	 *            The enum that signifies whether this lineType is a startPoint
	 *            or endPoint.
	 */
	public void addLineType(LineType str);

	/**
	 * 
	 * @param line
	 *            Adds a UMLLine line to Lines.
	 */
	public void addLine(UMLLine line);

	/**
	 * 
	 * @param id
	 *            The unique id of the line to determine which line needs to be
	 *            deleted.
	 */
	public void deleteLine(int id);
}
