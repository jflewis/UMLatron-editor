/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.millersville.umlatron.controller;

import edu.millersville.umlatron.model.LineType;
import edu.millersville.umlatron.model.State;
import edu.millersville.umlatron.model.UmlModel;
import edu.millersville.umlatron.view.AnchorPoint;
import edu.millersville.umlatron.view.Box;
import edu.millersville.umlatron.view.ClassBox;
import edu.millersville.umlatron.view.DiamondLine;
import edu.millersville.umlatron.view.UMLLine;
import edu.millersville.umlatron.view.UmlView;
import java.util.ArrayList;
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
    Stage stage;
    ArrayList<Node> clickedNodes = new ArrayList<>();

    public UmlatronController(Stage stage) {

        this.stage = stage;

        // set initial select state
        model.getStateProperty().addListener(
                (ObservableValue<? extends State> ov, State old_state,
                        State new_state) -> {

                    if (new_state != State.LINE) {
                        clickedNodes.clear();
                    }

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

        // filter out clicks on nodes for currently selected
        view.getEditPane().addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent e) {
                Node selectedNode = e.getPickResult().getIntersectedNode();
                Node filteredNode = checkIfPane(selectedNode);
                if (filteredNode != null) {
                    model.getCurrentlySelectedNodeProperty().setValue(filteredNode);
                    //if we are in the line state and the node clicked on is able to have a line attached to it continue
                    if (model.getStateProperty().get() == State.LINE && filteredNode instanceof AnchorPoint) {
                        clickedNodes.add(filteredNode);
                        if (clickedNodes.size() == 2) {
                            UMLLine lineTest = new UMLLine(
                                    (AnchorPoint) clickedNodes.get(0),
                                    (AnchorPoint) clickedNodes.get(1));
                            ((AnchorPoint) clickedNodes.get(0)).addLine(lineTest);
                            ((AnchorPoint) clickedNodes.get(1)).addLine(lineTest);
                            ((AnchorPoint) clickedNodes.get(0))
                                    .addLineType(LineType.START);
                            ((AnchorPoint) clickedNodes.get(1))
                                    .addLineType(LineType.END);
                            view.getEditPane().getChildren().add(lineTest);
                            clickedNodes.clear();

                        }
                    }
                }
            }
        });

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

    /**
     * Checks if what you clicked on is the pane
     *
     * @param n what you clicked on
     * @return the node or null if the pane
     */
    private Node checkIfPane(Node n) {
        if (n.getClass().equals(Pane.class)) {
            return null;
        } else {
            return n;
        }
    }

    private void setLineState() {
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
    }

    private void setSelectState() {
        view.getEditPane().setOnMouseClicked(null);
    }
}
