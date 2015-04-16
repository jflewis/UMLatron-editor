package edu.millersville.umlatron.view;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;

import edu.millersville.umlatron.Util.AnchorInfo;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

/**
*
* @author Matthew Hipszer
*
*/
public class UMLRecursiveLine extends Group implements java.io.Externalizable {

	AnchorInfo startPoint;
	AnchorInfo endPoint;
	ClassBox node;
	DoubleBinding deltaX, deltaY, distance;
	protected boolean dashed = false;
	protected ArrayList<Line> lines = new ArrayList<Line>();

	ChangeListener<Number> listener = (ObservableValue<? extends Number> ov,
			Number old_state, Number new_state) -> {
		calculateAnchorPoints();
	};

	/**
	 * 
	 * @param node
	 *            The node that this association is recursively pointing to.
	 */
	public UMLRecursiveLine(ClassBox node) {
		super();

		this.node = node;

		for (int i = 0; i < 4; ++i) {
			lines.add(new Line());
			lines.get(i).setCursor(Cursor.OPEN_HAND);
			lines.get(i).setStrokeWidth(2.0);
		}

		node.addRecursiveLine(this);

		startPoint = node.getNorthPoint();
		endPoint = node.getWestPoint();

		deltaX = lines.get(0).endXProperty()
				.subtract(lines.get(0).startXProperty());
		deltaY = lines.get(0).startYProperty()
				.subtract(lines.get(0).endYProperty());
		distance = deltaX.add(deltaY);

		distance.addListener(listener);

		calculateAnchorPoints();
		this.getChildren().addAll(lines.get(0), lines.get(1), lines.get(2), lines.get(3));
	}
	
	/**
	 * A default constructor used for loading
	 */
	public UMLRecursiveLine() {}

	/**
	 * 
	 * @return Returns the UMLRecursiveLine created by the method.
	 */
	public UMLRecursiveLine createLineFromLoad() {
		node.addRecursiveLine(this);

		startPoint = node.getNorthPoint();
		endPoint = node.getWestPoint();

		for (int i = 0; i < 4; ++i) {
			lines.add(new Line());
			lines.get(i).setCursor(Cursor.OPEN_HAND);
			lines.get(i).setStrokeWidth(2.0);
		}

		deltaX = lines.get(0).endXProperty()
				.subtract(lines.get(0).startXProperty());
		deltaY = lines.get(0).startYProperty()
				.subtract(lines.get(0).endYProperty());
		distance = deltaX.add(deltaY);

		distance.addListener(listener);

		calculateAnchorPoints();
		this.getChildren().addAll(lines.get(0), lines.get(1), lines.get(2), lines.get(3));
		return this;
	}

	/**
	 * Turns the line to a dashed line.
	 */
	protected void setDashed() {
		if (dashed == false) {
			for (int i = 0; i < lines.size(); ++i) {
				lines.get(i).getStrokeDashArray().addAll(9d, 9d, 9d, 9d);
			}
			dashed = true;
		}
	}

	/**
	 * Turns the line to a solid line.
	 */
	protected void setSolid() {
		for (int i = 0; i < lines.size(); ++i) {
			lines.get(i).getStrokeDashArray().clear();
		}
		dashed = false;
	}

	/**
	 * Deletes any references to the UMLRecursiveLine and then deletes itself.
	 */
	public void destroy() {
		node.removeRecursiveLine(this);

		Pane pane = (Pane) this.getParent();
		pane.getChildren().remove(this);
	}

	/**
	 * Calculate where all the lines must be placed based off of anchor points.
	 */
	final void calculateAnchorPoints() {

		AnchorInfo startAnchor = startPoint;
		AnchorInfo endAnchor = endPoint;

		lines.get(0).startXProperty().unbind();
		lines.get(0).startYProperty().unbind();
		lines.get(0).startXProperty().bind(startAnchor.getX());
		lines.get(0).startYProperty().bind(startAnchor.getY());
		lines.get(0).setEndX(lines.get(0).getStartX());
		lines.get(0).setEndY(lines.get(0).getStartY() - 25);

		lines.get(1).setStartX(lines.get(0).getEndX());
		lines.get(1).setStartY(lines.get(0).getEndY());
		lines.get(1).setEndX(lines.get(0).getEndX() - (node.getWidth() / 2 + 25));
		lines.get(1).setEndY(lines.get(0).getEndY());

		lines.get(2).setStartX(lines.get(1).getEndX());
		lines.get(2).setStartY(lines.get(1).getEndY());
		lines.get(2).setEndX(lines.get(1).getEndX());
		lines.get(2).setEndY(lines.get(1).getEndY() + (node.getHeight() / 2 + 25));

		lines.get(3).setStartX(lines.get(2).getEndX());
		lines.get(3).setStartY(lines.get(2).getEndY());
		lines.get(3).setEndX(lines.get(2).getEndX() + 25);
		lines.get(3).setEndY(lines.get(2).getEndY());
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		node = (ClassBox) in.readObject();
		dashed = in.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(node);
		out.writeBoolean(dashed);
	}

}
