package edu.millersville.umlatron.view;

import edu.millersville.umlatron.view.UMLLine;

/**
 * 
 * @author Matthew Hipszer
 *
 */
public class UMLDottedLine extends UMLLine
{
	/**
	 * 
	 * @param b1 The box that the starting point of the line is attached to.
     * @param b2 The box that the ending point of the line is attached to.
	 */
	public UMLDottedLine(Box b1, Box b2) 
	{
		super(b1, b2);
		this.getStrokeDashArray().addAll(5d, 5d, 5d, 5d);
	}

}
