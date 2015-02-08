package edu.millersville.umlatron;

//class created by Matt
public class UMLDottedLine extends UMLLine{

	public UMLDottedLine(double x1, double y1, double x2, double y2) {
		super(x1, y1, x2, y2);
		this.getStrokeDashArray().addAll(5d, 5d, 5d, 5d);
	}

}
