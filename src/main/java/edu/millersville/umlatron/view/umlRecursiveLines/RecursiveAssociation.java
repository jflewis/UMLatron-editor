package edu.millersville.umlatron.view.umlRecursiveLines;

import edu.millersville.umlatron.view.AnchorPoint;
import edu.millersville.umlatron.view.ClassBox;
import edu.millersville.umlatron.view.SelectedPanel;
import edu.millersville.umlatron.view.umlRecursiveLines.UMLRecursiveLine;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
*
* @author Matthew Hipszer
*
*/
public class RecursiveAssociation extends UMLRecursiveLine implements
		SelectedPanel, java.io.Serializable {

	Polygon polygon = new Polygon();
        Boolean filled = false;


	/**
	 * 
	 * @param node
	 *            The node that this association is recursively pointing to.
	 */
	public RecursiveAssociation(AnchorPoint node) {
		super(node);
		createPolygon();
	}

	/**
	 * A default constructor used for loading
	 */
	public RecursiveAssociation() {
	}

	@Override
	public RecursiveAssociation createLineFromLoad() {
		super.createLineFromLoad();
		createPolygon();
		return this;
	}

	/**
	 * Creates the polygon used at the end of the line.
	 */
	private void createPolygon() {

		polygon.getPoints().addAll(
				new Double[] { 0.0, 0.0, -12.0, -7.0, -24.0, 0.0, -12.0, 7.0 });
		polygon.setFill(Color.WHITE);
		polygon.setStrokeWidth(3);
		polygon.setStroke(Color.NAVY);

		this.getChildren().add(polygon);

		updateHead();

		distance.removeListener(listener);
		distance.addListener((ObservableValue<? extends Number> ov,
				Number old_state, Number new_state) -> {

			calculateAnchorPoints();
			updateHead();

		});

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
			polygon.setFill(Color.NAVY);

		}
	}

	/**
	 * Updates the location of the polygon at the head of the line.
	 */
	public void updateHead() {
		// double slopeInDegrees = (Math.toDegrees(Math.atan2(line.getStartY() -
		// line.getEndY(), line.getEndX() - line.getStartX())));
		// rotate.setAngle(-slopeInDegrees);
		polygon.setTranslateX(lines.get(3).getEndX());
		polygon.setTranslateY(lines.get(3).getEndY());
	}

	@Override
	public void createAndGeneratePanel(HBox h) {

		h.getChildren().clear();
		DropShadow shadow = new DropShadow();

		Label label = new Label("Currently on:  ");
        label.setId("currentPanel");
        Image labelGeneralization = new Image("/images/Association.png", 35, 35, false, false);
        ImageView iv1 = new ImageView();
        iv1.setImage(labelGeneralization);
        label.setContentDisplay(ContentDisplay.BOTTOM);
        label.setGraphic(new ImageView(labelGeneralization));
		
		Button aggregation = new Button("Aggregation");
		Image aggregationImg = new Image("/images/Association.png", 35, 35, false, false); 
        ImageView iv3 = new ImageView();
        iv3.setImage(aggregationImg);
        aggregation.setContentDisplay(ContentDisplay.RIGHT);
        aggregation.setGraphic(new ImageView(aggregationImg));
		aggregation.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(aggregation, Priority.ALWAYS);
		aggregation.setOnAction((ActionEvent e) -> {
			setFillWhite();
			  label.setContentDisplay(ContentDisplay.BOTTOM);
	            label.setGraphic(new ImageView(aggregationImg));
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
		Image composition = new Image("/images/Composition.png", 35, 35, false, false); 
        ImageView iv4 = new ImageView();
        iv4.setImage(composition);
        comp.setContentDisplay(ContentDisplay.RIGHT);
        comp.setGraphic(new ImageView(composition));
		comp.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(comp, Priority.ALWAYS);
		comp.setOnAction((ActionEvent e) -> {
			setFillBlack();
			  label.setContentDisplay(ContentDisplay.BOTTOM);
	            label.setGraphic(new ImageView(composition));
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


		h.getChildren().addAll(label, aggregation, comp, deleteB);

	}

}
