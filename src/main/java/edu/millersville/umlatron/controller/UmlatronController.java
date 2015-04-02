/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.millersville.umlatron.controller;

import edu.millersville.umlatron.model.*;
import edu.millersville.umlatron.view.*;
import javafx.beans.value.ObservableValue;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Toggle;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
                            UMLArrowLine lineTest = new UMLArrowLine(
                                    (AnchorPoint) clickedNodes.get(0),
                                    (AnchorPoint) clickedNodes.get(1));
                            ((AnchorPoint) clickedNodes.get(0)).addLine(lineTest);
                            ((AnchorPoint) clickedNodes.get(1)).addLine(lineTest);
                            ((AnchorPoint) clickedNodes.get(0))
                                    .addLineType(LineType.START);
                            ((AnchorPoint) clickedNodes.get(1))
                                    .addLineType(LineType.END);
                            view.getEditPane().getChildren().addAll(lineTest,lineTest.getLine1(),lineTest.getLine2());
                            clickedNodes.clear();

                        }
                    }
                }
            }
        });

        model.getCurrentlySelectedNodeProperty().addListener((ObservableValue<? extends Node> ov,
                Node last_selected, Node new_selected) -> {
            
                if(new_selected instanceof SelectedPanel){
                    ((SelectedPanel)new_selected).createAndGeneratePanel(view.getCurrentlySelectedPane());
                   
                }

            /*
                    if (new_selected instanceof Se) {
                        
                    } else if (new_selected instanceof ClassBox) {
                        
                    } else if (new_selected instanceof UMLArrowLine) {
                        
                    } else if (new_selected instanceof DiamondLine) {
                        
                    } else {
                        //wait what?
                    }
            */

                });
        
        
        /**
         * almost scrolling because why not
         */
         view.getEditPane().setOnScroll(new EventHandler<ScrollEvent>() {
            @Override public void handle(ScrollEvent event) {
                double scaleFactor = (event.getDeltaY() > 0) ? 1.5 : 1 / 1.5;
                
                if(view.getEditPane().getScaleX() * scaleFactor >= 1.0){
                    view.getEditPane().setScaleX(view.getEditPane().getScaleX() * scaleFactor);
                    view.getEditPane().setScaleY(view.getEditPane().getScaleY() * scaleFactor);
                }
                
            }
        });

    }

    public BorderPane getView() {
        return view;
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
