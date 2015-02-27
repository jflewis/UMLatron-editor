package edu.millersville.umlatron;

/**
 * 
 * @author Matthew Hipszer
 *
 */
public class UMLDottedLine extends UMLLine
{
	/**
	 * Creates a dotted line.
	 * @param x1			x value of starting point.
     * @param y1			y value of starting point.
     * @param x2			x value of ending point.
     * @param y2			y value of ending point.
	 */
	public UMLDottedLine(double x1, double y1, double x2, double y2) 
	{
		super(x1, y1, x2, y2);
		this.getStrokeDashArray().addAll(5d, 5d, 5d, 5d);
	}

}
