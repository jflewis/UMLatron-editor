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
public class RecursiveGeneralization extends UMLRecursiveLine implements
		SelectedPanel, java.io.Serializable {

	Polygon polygon = new Polygon();
	Boolean filled = false;

	/**
	 * 
	 * @param node
	 *            The node that this association is recursively pointing to.
	 */
	public RecursiveGeneralization(AnchorPoint node) {
		super(node);
		createPolygon();
	}

	/**
	 * A default constructor used for loading
	 */
	public RecursiveGeneralization() {}

	@Override
	public RecursiveGeneralization createLineFromLoad() {
		super.createLineFromLoad();
		createPolygon();
		return this;
	}

	/**
	 * Creates the polygon used at the end of the line.
	 */
	private void createPolygon() {

		polygon.getPoints().addAll(
				new Double[] { 0.0, 0.0, -12.0, -7.0, -12.0, 7.0 });
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
	        Image labelGeneralization = new Image("/images/Generalization.png", 35, 35, false, false);
	        ImageView iv1 = new ImageView();
	        iv1.setImage(labelGeneralization);
	        label.setContentDisplay(ContentDisplay.BOTTOM);
	        
	        Button setDashed = new Button("Dashed  ");
	        Image DashedGeneralization = new Image("/images/DashedGeneralization.png", 35, 35, false, false); 
	        ImageView iv3 = new ImageView();
	        iv3.setImage(DashedGeneralization);
	        setDashed.setContentDisplay(ContentDisplay.RIGHT);
	        setDashed.setGraphic(new ImageView(DashedGeneralization));
	        setDashed.setMaxWidth(Double.MAX_VALUE);
	        setDashed.setMaxWidth(Double.MAX_VALUE);
	        HBox.setHgrow(setDashed, Priority.ALWAYS);
	        setDashed.setOnAction((ActionEvent e) -> {
	            setDashed();
	            label.setContentDisplay(ContentDisplay.BOTTOM);
	            label.setGraphic(new ImageView(labelGeneralization));

	        });
	        setDashed.addEventHandler(MouseEvent.MOUSE_ENTERED,
	                new EventHandler<MouseEvent>() {
	                    @Override
	                    public void handle(MouseEvent e) {
	                        setDashed.setEffect(shadow);
	                    }
	                });
	        setDashed.addEventHandler(MouseEvent.MOUSE_EXITED,
	                new EventHandler<MouseEvent>() {
	                    @Override
	                    public void handle(MouseEvent e) {
	                        setDashed.setEffect(null);
	                    }
	                });

	        Button setSolid = new Button("Solid  ");
	        Image Generalization = new Image("/images/Generalization.png", 35, 35, false, false);
	        ImageView iv2 = new ImageView();
	        iv2.setImage(Generalization);
	        setSolid.setContentDisplay(ContentDisplay.RIGHT);
	        setSolid.setGraphic(new ImageView(Generalization));
	        setSolid.setMaxWidth(Double.MAX_VALUE);
	        HBox.setHgrow(setSolid, Priority.ALWAYS);
	        setSolid.setOnAction((ActionEvent e) -> {
	           setSolid();
	           label.setContentDisplay(ContentDisplay.BOTTOM);
	           label.setGraphic(new ImageView(Generalization));
	        });
	        setSolid.addEventHandler(MouseEvent.MOUSE_ENTERED,
	                new EventHandler<MouseEvent>() {
	                    @Override
	                    public void handle(MouseEvent e) {
	                        setSolid.setEffect(shadow);
	                    }
	                });
	        setSolid.addEventHandler(MouseEvent.MOUSE_EXITED,
	                new EventHandler<MouseEvent>() {
	                    @Override
	                    public void handle(MouseEvent e) {
	                        setSolid.setEffect(null);
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
	        
	       


	        h.getChildren().addAll(label,setDashed, setSolid,deleteB);

	    }

}
