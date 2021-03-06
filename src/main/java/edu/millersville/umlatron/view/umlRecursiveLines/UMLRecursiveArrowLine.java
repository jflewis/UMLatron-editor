package edu.millersville.umlatron.view.umlRecursiveLines;

import edu.millersville.umlatron.view.AnchorPoint;
import edu.millersville.umlatron.view.SelectedPanel;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;

/**
*
* @author Matthew Hipszer
*
*/
public class UMLRecursiveArrowLine extends UMLRecursiveLine implements
		SelectedPanel {

	final private Line minSpreadLine = new Line(0, 0, -10, -10);
	final private Line maxSpreadLine = new Line(0, 0, -10, 10);
	final Group group = new Group();

	/**
	 * 
	 * @param node
	 *            The node that this association is recursively pointing to.
	 */
	public UMLRecursiveArrowLine(AnchorPoint node) {
		super(node);
		createPolygon();
	}

	/**
	 * A default constructor used for loading
	 */
	public UMLRecursiveArrowLine() {
	}

	@Override
	public UMLRecursiveArrowLine createLineFromLoad() {
		super.createLineFromLoad();
		createPolygon();
		return this;

	}

	/**
	 * Creates the polygon used at the end of the line.
	 */
	private void createPolygon() {

		group.getChildren().addAll(minSpreadLine, maxSpreadLine);
		this.getChildren().add(group);
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
		group.setTranslateX(lines.get(3).getEndX());
		group.setTranslateY(lines.get(3).getEndY());
	}

	  @Override
	    public void createAndGeneratePanel(HBox h) {

	        h.getChildren().clear();
	        DropShadow shadow = new DropShadow();
	        
	        Label label = new Label("Currently on:  ");
	        label.setId("currentPanel");
	        Image PlainClassBox = new Image("/images/SolidLine.png", 35, 35, false, false);
	        ImageView iv1 = new ImageView();
	        iv1.setImage(PlainClassBox);
	        label.setContentDisplay(ContentDisplay.BOTTOM);
	        label.setGraphic(new ImageView(PlainClassBox));
	        HBox.setHgrow(label, Priority.ALWAYS);
	        label.setMaxWidth(Double.MAX_VALUE);

	        Button setDashed = new Button("Dashed  ");
	        Image DashedLine = new Image("/images/DashedLine.png", 35, 35, false, false);
	        ImageView iv2 = new ImageView();
	        iv2.setImage(DashedLine);
	        setDashed.setContentDisplay(ContentDisplay.RIGHT);
	        setDashed.setGraphic(new ImageView(DashedLine));
	        setDashed.setMaxWidth(Double.MAX_VALUE);
	        HBox.setHgrow(setDashed, Priority.ALWAYS);
	        setDashed.setOnAction((ActionEvent e) -> {
	            setDashed();
	            label.setContentDisplay(ContentDisplay.BOTTOM);
	            label.setGraphic(new ImageView(DashedLine));


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
	        Image SolidLine = new Image("/images/SolidLine.png", 35, 35, false, false);
	        ImageView iv3 = new ImageView();
	        iv3.setImage(SolidLine);
	        setSolid.setContentDisplay(ContentDisplay.RIGHT);
	        setSolid.setGraphic(new ImageView(SolidLine));
	        setSolid.setMaxWidth(Double.MAX_VALUE);
	        HBox.setHgrow(setSolid, Priority.ALWAYS);
	        setSolid.setOnAction((ActionEvent e) -> {
	            setSolid();
	            label.setContentDisplay(ContentDisplay.BOTTOM);
	            label.setGraphic(new ImageView(SolidLine));
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
