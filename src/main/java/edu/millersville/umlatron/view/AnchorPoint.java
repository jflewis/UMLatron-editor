package edu.millersville.umlatron.view;

import edu.millersville.umlatron.view.umlLines.UMLLine;
import edu.millersville.umlatron.Util.AnchorInfo;


/**
 *
 * @author Matthew Hipszer
 *
 */
public interface AnchorPoint {

	/**
	 * 
	 * @return Returns the northern most anchor point.
	 */
	public AnchorInfo getNorthPoint();

	/**
	 * 
	 * @return Returns the southern most anchor point.
	 */
	public AnchorInfo getSouthPoint();

	/**
	 * 
	 * @return Returns the eastern most anchor point.
	 */
	public AnchorInfo getEastPoint();


	/**
	 * 
	 * @return Returns the western most anchor point.
	 */
	public AnchorInfo getWestPoint();

	/**
	 * 
	 * @param line
	 *            The line to be removed.
	 */
	public void removeLine(UMLLine line);

	/**
	 * 
	 * @param line
	 *            The line to be added.
	 */
	public void addLine(UMLLine line);

}
