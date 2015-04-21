package edu.millersville.umlatron.view.umlLines;

import edu.millersville.umlatron.view.AnchorPoint;
import edu.millersville.umlatron.view.ClassBox;
import edu.millersville.umlatron.view.SelectedPanel;
import edu.millersville.umlatron.view.umlLines.UMLLine;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;

/**
 *
 * @author Matthew Hipszer
 *
 */
public class Association extends UMLLine implements SelectedPanel,
		java.io.Serializable {

	Rotate rotate = new Rotate();
	Polygon polygon = new Polygon();
	Boolean filled = false;

	/**
	 *
	 * @param a1
	 *            The AnchorPoint that the starting point of the line is
	 *            attached to.
	 * @param a2
	 *            The AnchorPoint that the ending point of the line is attached
	 *            to.
	 */
	public Association(AnchorPoint a1, AnchorPoint a2) {
		super(a1, a2);
		createPolygon();

	}

	/**
	 * A default constructor used for loading.
	 */
	public Association() {
	}

	@Override
	public Association createLineFromLoad() {
		super.createLineFromLoad();
		createPolygon();
		return this;
	}

	/**
	 * Creates the polygon used at the end of the line.
	 */
	final void createPolygon() {
		polygon.getPoints().addAll(
				new Double[] { 0.0, 0.0, -12.0, -7.0, -24.0, 0.0, -12.0, 7.0

				});

		polygon.setFill(Color.WHITE);
		polygon.setStrokeWidth(1);
		polygon.setStroke(Color.BLACK);
		polygon.getTransforms().add(rotate);
		this.getChildren().add(polygon);
		distance.removeListener(listener);
		distance.addListener((ObservableValue<? extends Number> ov,
				Number old_state, Number new_state) -> {

			calculateAnchorPoints();
			updateHead();

		});

		updateHead();

	}

	@Override
	public void updateHead() {
		double slopeInDegrees = (Math.toDegrees(Math.atan2(line.getStartY()
				- line.getEndY(), line.getEndX() - line.getStartX())));
		rotate.setAngle(-slopeInDegrees);
		polygon.setTranslateX(line.getEndX());
		polygon.setTranslateY(line.getEndY());
	}

	/**
	 * Fills the polygon with the color white.
	 */
	private void setFillWhite() {
		if (polygon != null) {
			filled = false;
			polygon.setFill(Color.WHITE);

		}
	}

	/**
	 * Fills the polygon with the color black.
	 */
	private void setFillBlack() {
		if (polygon != null) {
			filled = true;
			polygon.setFill(Color.BLACK);

		}
	}

	/**
	 * creates the currently selected panel for this Node
	 * 
	 * @param h
	 *            the views HBox
	 */
	@Override
	public void createAndGeneratePanel(HBox h) {

		h.getChildren().clear();
		DropShadow shadow = new DropShadow();

		Button aggregation = new Button("Aggregation");
		aggregation.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(aggregation, Priority.ALWAYS);
		aggregation.setOnAction((ActionEvent e) -> {
			setFillWhite();

		});
		aggregation.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						aggregation.setEffect(shadow);
					}
				});
		aggregation.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						aggregation.setEffect(null);
					}
				});

		Button comp = new Button("Composition");
		comp.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(comp, Priority.ALWAYS);
		comp.setOnAction((ActionEvent e) -> {
			setFillBlack();
		});
		comp.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						comp.setEffect(shadow);
					}
				});
		comp.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						comp.setEffect(null);
					}
				});

		Button deleteB = new Button("Delete");
		deleteB.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(deleteB, Priority.ALWAYS);
		deleteB.setOnAction((ActionEvent e) -> {
			destroy();
			h.getChildren().clear();
		});
		deleteB.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						deleteB.setEffect(shadow);
					}
				});
		deleteB.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						deleteB.setEffect(null);
					}
				});

		Label label = new Label("Currently selected node : Association ");

		h.getChildren().addAll(label, aggregation, comp, deleteB);

	}
}
