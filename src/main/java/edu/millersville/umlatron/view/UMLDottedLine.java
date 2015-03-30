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
	public UMLDottedLine(AnchorPoint a1, AnchorPoint a2) 
	{
		super(a1, a2);
		this.getStrokeDashArray().addAll(5d, 5d, 5d, 5d);
	}

}
