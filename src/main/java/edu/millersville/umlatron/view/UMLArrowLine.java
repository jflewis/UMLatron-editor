package edu.millersville.umlatron.view;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import edu.millersville.umlatron.view.UMLLine;

/**
 * 
 * @author Matthew Hipszer
 *
 */
public class UMLArrowLine extends UMLLine {

	private Line minSpreadLine;
	private Line maxSpreadLine;

	/**
	 * 
	 * @param a1
	 *            The AnchorPoint that the starting point of the line is
	 *            attached to.
	 * @param a2
	 *            The AnchorPoint that the ending point of the line is attached
	 *            to.
	 */
	public UMLArrowLine(AnchorPoint a1, AnchorPoint a2) {
		super(a1, a2);
		// TODO
		double slopeInDegrees = 0;

		Point2D minSpreadLineStart = null;
		Point2D minSpreadLineEnd = null;
		Point2D maxSpreadLineStart = null;
		Point2D maxSpreadLineEnd = null;
		double minSlopeInDegrees = (slopeInDegrees - 135);
		double maxSlopeInDegrees = (slopeInDegrees + 135);
		if (minSlopeInDegrees < 0) {
			minSlopeInDegrees += 360;
		}
		if (maxSlopeInDegrees >= 360) {
			maxSlopeInDegrees -= 360;
		}
		double minShotSlope = Math.tan(Math.toRadians(minSlopeInDegrees));
		double maxShotSlope = Math.tan(Math.toRadians(maxSlopeInDegrees));
		double k1, k2;
		double lineLength = 20;

		System.out.println(minSlopeInDegrees);
		System.out.println(maxSlopeInDegrees);

		// up
		if (minSlopeInDegrees == 90) {
			k1 = (lineLength / (Math.sqrt(1 + minShotSlope * minShotSlope)))
					* -1;
		}
		// up-left
		else if (minSlopeInDegrees > 90 && minSlopeInDegrees < 180) {
			k1 = (lineLength / (Math.sqrt(1 + minShotSlope * minShotSlope)))
					* -1;
		}
		// left
		else if (minSlopeInDegrees == 180) {
			k1 = (lineLength / (Math.sqrt(1 + minShotSlope * minShotSlope)))
					* -1;
		}
		// down-left
		else if (minSlopeInDegrees > 180 && minSlopeInDegrees < 270) {
			k1 = (lineLength / (Math.sqrt(1 + minShotSlope * minShotSlope)))
					* -1;
		} else {
			k1 = lineLength / (Math.sqrt(1 + minShotSlope * minShotSlope));
		}

		if (maxSlopeInDegrees == 90) {
			k2 = (lineLength / (Math.sqrt(1 + maxShotSlope * maxShotSlope)))
					* -1;
		}
		// up-left
		else if (maxSlopeInDegrees > 90 && maxSlopeInDegrees < 180) {
			k2 = (lineLength / (Math.sqrt(1 + maxShotSlope * maxShotSlope)))
					* -1;
		}
		// left
		else if (maxSlopeInDegrees == 180) {
			k2 = (lineLength / (Math.sqrt(1 + maxShotSlope * maxShotSlope)))
					* -1;
		}
		// down-left
		else if (maxSlopeInDegrees > 180 && maxSlopeInDegrees < 270) {
			k2 = (lineLength / (Math.sqrt(1 + maxShotSlope * maxShotSlope)))
					* -1;
		} else {
			k2 = lineLength / (Math.sqrt(1 + maxShotSlope * maxShotSlope));
		}

		minSpreadLineStart = new Point2D(this.getEndX(), this.getEndY());
		minSpreadLineEnd = new Point2D((int) (this.getEndX() + k1),
				(int) (this.getEndY() + k1 * minShotSlope));
		maxSpreadLineStart = new Point2D(this.getEndX(), this.getEndY());
		maxSpreadLineEnd = new Point2D((int) (this.getEndX() + k2),
				(int) (this.getEndY() + k2 * maxShotSlope));

		minSpreadLine = new Line(minSpreadLineStart.getX(),
				minSpreadLineStart.getY(), minSpreadLineEnd.getX(),
				minSpreadLineEnd.getY());
		maxSpreadLine = new Line(maxSpreadLineStart.getX(),
				maxSpreadLineStart.getY(), maxSpreadLineEnd.getX(),
				maxSpreadLineEnd.getY());
	}

	/**
	 * 
	 * @return Returns the line 135 degrees clockwise to the main line.
	 */
	public Node getLine1() {
		return minSpreadLine;
	}

	/**
	 * 
	 * @return Returns the line 135 degrees counterclockwise to the main line.
	 */
	public Node getLine2() {
		return maxSpreadLine;
	}

}
