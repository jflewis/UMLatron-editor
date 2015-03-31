/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.millersville.umlatron.controller;

import edu.millersville.umlatron.model.State;
import edu.millersville.umlatron.model.UmlModel;
import edu.millersville.umlatron.view.AnchorPoint;
import edu.millersville.umlatron.view.Box;
import edu.millersville.umlatron.view.ClassBox;
import edu.millersville.umlatron.view.DiamondLine;
import edu.millersville.umlatron.view.UMLLine;
import edu.millersville.umlatron.view.UmlView;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Toggle;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author John Lewis
 */
public class UmlatronController {

	UmlModel model = new UmlModel();
	UmlView view = new UmlView();
	Pane editPane;
	Stage stage;
	
	Node temp1, temp2;
	int count = 0;
	boolean line = false;

	EventHandler<MouseEvent> createClassBox = (event) -> {
		double x = event.getSceneX();
		double y = event.getSceneY();
		System.out.println("You created a ClassBox at " + x + " , " + y);
		view.getEditPane().getChildren().add(new ClassBox(x, y));
	};

	public UmlatronController(Stage stage) {

		this.editPane = view.getEditPane();
		this.stage = stage;

		// set intial select state
		model.getStateProperty().addListener(
				(ObservableValue<? extends State> ov, State old_state,
						State new_state) -> {
					// System.out.println("model state has changed");

					switch (new_state) {
					case SELECT:
						System.out.println("state changed to select");
						setSelectState();
						break;

					case LINE:
						System.out.println("state changed to line");
						setLineState();
						break;

					case ASSOCIATION:
						System.out.println("state set to association");
						setTestLineState();
						break;

					case CLASSBOX:
						System.out.println("state set to classbox");
						setClassBoxState();
						break;
					}

				});

		// Monitors the change of the toggle buttons
		view.getStateToggle()
				.selectedToggleProperty()
				.addListener(
						(ObservableValue<? extends Toggle> ov, Toggle toggle,
								Toggle new_toggle) -> {

							if (new_toggle == null) {
								System.out
										.print("nothing is selected, select is pressed");
								// model.setState(State.SELECT);
							} else {
								switch ((State) new_toggle.getUserData()) {
								case SELECT:
									// System.out.println("select mode");
									model.setState(State.SELECT);
									break;

								case LINE:
									System.out.println("Line mode");
									model.setState(State.LINE);
									break;

								case ASSOCIATION:
									// System.out.println("association mode");
									model.setState(State.ASSOCIATION);
									break;

								case CLASSBOX:
									// System.out.println("classbox mode");
									model.setState(State.CLASSBOX);
									break;

								default:
									// something went wrong
									break;

								}

							}
						});

		
		view.getEditPane().addEventFilter(MouseEvent.MOUSE_PRESSED,
				new EventHandler<MouseEvent>() 
				{
					@Override
					public void handle(MouseEvent e) 
					{
						if (line) 
						{
							Node selectedNode = e.getPickResult().getIntersectedNode();
							if (selectedNode != null && selectedNode instanceof AnchorPoint) 
							{
								if (count == 0) 
								{
									++count;
									temp1 = selectedNode;
								} 
								else if (count == 1 && temp1 != selectedNode) 
								{
									temp2 = selectedNode;
									count = 0;
									// create line
									UMLLine lineTest = new UMLLine((AnchorPoint) temp1, (AnchorPoint) temp2);
									((AnchorPoint) temp1).addLine(lineTest);
									((AnchorPoint) temp2).addLine(lineTest);
									((AnchorPoint) temp1).addLineType("start");
									((AnchorPoint) temp2).addLineType("end");
									view.getEditPane().getChildren().add(lineTest);
									temp1 = null;
									temp2 = null;
								}
							}
						}
					}
				});
				

		// filter out clicks on nodes for currently selected
		/*
		 * view.getEditPane().addEventFilter(MouseEvent.MOUSE_PRESSED, new
		 * EventHandler<MouseEvent>(){
		 * 
		 * @Override public void handle(MouseEvent e){ Node selectedNode =
		 * e.getPickResult().getIntersectedNode(); Node filteredNode =
		 * whatNodeAmI(selectedNode); if(filteredNode != null){
		 * model.getCurrentlySelectedNodeProperty().setValue(filteredNode); } }
		 * });
		 */
	}

	public BorderPane getView() {
		return view;
	}

	// TODO: Implement this a a visitor pattern so we do not have to do all this
	// typecasting
	private Node whatNodeAmI(Node n) {
		if (n instanceof Box) {
			return ((Box) n);
		} else if (n instanceof ClassBox) {
			return (ClassBox) n;
		} else if (n instanceof UMLLine) {
			return (UMLLine) n;
		} else if (n instanceof DiamondLine) {
			return (DiamondLine) n;
		} else {
			return null;
		}
	}

	private void setTestLineState() {

		EventHandler<MouseEvent> createBox = (event) -> {
			if (event.getPickResult().getIntersectedNode() != null) {
				double x = event.getX();
				double y = event.getY();
				Box boxTest = new Box(Color.GRAY, x, y);
				view.getEditPane().getChildren().add(boxTest);
			}
		};
		view.getEditPane().setOnMouseClicked(createBox);
		line = false;
	}

	
	private void setLineState() {
    
        //This caused some weird errors if you toggle between states a lot.
        //The error allows you to create lines with one click with the startpoint at wherever the last endpoint was.
		/*
		view.getEditPane().addEventFilter(MouseEvent.MOUSE_PRESSED,
				new EventHandler<MouseEvent>() 
				{
					@Override
					public void handle(MouseEvent e) 
					{
						Node selectedNode = e.getPickResult().getIntersectedNode();
						if (selectedNode != null && selectedNode instanceof AnchorPoint) 
						{
							if (count == 0) 
							{
								++count;
								temp1 = selectedNode;
							} 
							else if (count == 1 && temp1 != selectedNode) 
							{
								temp2 = selectedNode;
								count = 0; 
								//create line
								UMLLine lineTest = new UMLLine((AnchorPoint) temp1, (AnchorPoint) temp2);
								((AnchorPoint) temp1).addLine(lineTest);
								((AnchorPoint) temp2).addLine(lineTest);
								((AnchorPoint) temp1).addLineType("start");
								((AnchorPoint) temp2).addLineType("end");
								view.getEditPane().getChildren().add(lineTest);
								temp1 = null;
								temp2 = null;
							}
						}
					}
				});
		*/
		line = true;
		view.getEditPane().setOnMouseClicked(null);
	}

	private void setClassBoxState() {

		EventHandler<MouseEvent> createClassBox = (event) -> {
			double x = event.getX();
			double y = event.getY();
			System.out.println("You created a ClassBox at " + x + " , " + y);
			view.getEditPane().getChildren().add(new ClassBox(x, y));
		};

		view.getEditPane().setOnMouseClicked(createClassBox);
		line = false;

	}

	private void setSelectState() {
		view.getEditPane().setOnMouseClicked(null);
		line = false;
	}
}
/**
 *
 *
 *
 * EventHandler<MouseEvent> drawLine = (event) -> { double x =
 * event.getSceneX(); double y = event.getSceneY();
 * System.out.println(event.getEventType()); System.out.println("You created a
 * line starting at " + x + " , " + y); lines = new UMLLine(x, y, x, y);
 * root.getChildren().add(lines); };
 *
 * EventHandler<MouseEvent> updateLine = (event) -> { double x =
 * event.getSceneX(); double y = event.getSceneY(); lines.setEndX(x);
 * lines.setEndY(y); };
 *
 * EventHandler<MouseEvent> drawDottedLine = (event) -> { double x =
 * event.getSceneX(); double y = event.getSceneY();
 * System.out.println(event.getEventType()); System.out.println("You created a
 * dotted line starting at " + x + " , " + y); dottedLines = new
 * UMLDottedLine(x, y, x, y); root.getChildren().add(dottedLines); };
 *
 * EventHandler<MouseEvent> updateDottedLine = (event) -> { double x =
 * event.getSceneX(); double y = event.getSceneY(); dottedLines.setEndX(x);
 * dottedLines.setEndY(y); };
 *
 *
 *
 *
 * }; EventHandler<MouseEvent> createDiamndLine = (event) -> { //only place if
 * not on a node if (event.getPickResult().getIntersectedNode() == null) {
 * double x = event.getSceneX(); double y = event.getSceneY();
 * System.out.println("You created a diamandLine at " + x + " , " + y);
 * root.getChildren().add(new DiamondLine(x, y)); }
 *
 * };
 *
 * // Menu items ----------------------------------- MenuItem itemCreateBox =
 * new MenuItem("Boxes"); itemCreateBox.setOnAction((event) -> {
 * scene.setOnMousePressed(null); scene.setOnMouseReleased(null);
 * scene.setOnMouseDragged(null); scene.setOnMouseClicked(createBox); });
 *
 * MenuItem itemDiamandLine = new MenuItem("DimandLine");
 * itemDiamandLine.setOnAction((event) -> { scene.setOnMousePressed(null);
 * scene.setOnMouseReleased(null); scene.setOnMouseDragged(null);
 * scene.setOnMouseClicked(createDiamndLine); });
 *
 * MenuItem itemCreateDottedLine = new MenuItem("Dotted Lines");
 * itemCreateDottedLine.setOnAction((event) -> { scene.setOnMouseClicked(null);
 * scene.setOnMousePressed(drawDottedLine);
 * scene.setOnMouseDragged(updateDottedLine);
 * scene.setOnMouseReleased(updateDottedLine);
 *
 * });
 *
 * MenuItem itemCreateLine = new MenuItem("Lines");
 * itemCreateLine.setOnAction((event) -> { scene.setOnMouseClicked(null);
 * scene.setOnMousePressed(drawLine); scene.setOnMouseDragged(updateLine);
 * scene.setOnMouseReleased(updateLine);
 *
 * });
 *
 * MenuItem itemCreateClassBox = new MenuItem("ClassBox");
 * itemCreateClassBox.setOnAction((event) -> { scene.setOnMousePressed(null);
 * scene.setOnMouseReleased(null); scene.setOnMouseDragged(null);
 * scene.setOnMouseClicked(createClassBox);
 *
 * });
 *
 */

