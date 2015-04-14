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
import javafx.stage.Stage;

/**
 *
 * @author John Lewis
 */
public class UmlatronController {

    UmlModel model = new UmlModel();
    UmlView view = new UmlView(this);
    Stage stage;
    ArrayList<Node> clickedNodes = new ArrayList<>();

    public UmlatronController(Stage stage) {

        this.stage = stage;

        // set initial select state
        model.getSelectStateProperty().addListener(
                (ObservableValue<? extends SelectState> ov, SelectState old_state,
                        SelectState new_state) -> {

                    if (new_state != SelectState.LINE) {
                        clickedNodes.clear();
                    }

                    switch (new_state) {
                        case SELECT:
                            setSelectState();
                            break;

                        case LINE:
                            setLineState();
                            break;

                        case ASSOCIATION:
                            setLineState();
                            break;

                        case GENERALIZATION:
                            setLineState();
                            break;

                        case CLASSBOX:
                            setClassBoxState();
                            break;
                    }

                });

        // Monitors the change of the toggle buttons
        view.getStateToggle().selectedToggleProperty().addListener(
                (ObservableValue<? extends Toggle> ov, Toggle toggle,
                        Toggle new_toggle) -> {

                    if (new_toggle == null) {
                        //nothing
                    } else {
                        switch ((SelectState) new_toggle.getUserData()) {
                            case SELECT:
                                model.setSelectState(SelectState.SELECT);
                                break;

                            case LINE:
                                model.setSelectState(SelectState.LINE);
                                break;

                            case ASSOCIATION:
                                model.setSelectState(SelectState.ASSOCIATION);
                                break;

                            case CLASSBOX:
                                model.setSelectState(SelectState.CLASSBOX);
                                break;

                            case GENERALIZATION:
                                model.setSelectState(SelectState.GENERALIZATION);
                                break;

                            default:
                                // something went wrong
                                break;

                        }

                    }
                });

        // listens for all presses on the pane, we use this to see what nodes are being pressed on
        view.getEditPane().addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent e) {
                Node selectedNode = e.getPickResult().getIntersectedNode();
                Node filteredNode = checkIfPane(selectedNode);
                // System.out.println(filteredNode.toString());
                if (filteredNode != null) {
                    model.getCurrentlySelectedNodeProperty().setValue(filteredNode);
                    //if we are in the line state and the node clicked on is able to have a line attached to it continue
                    if (model.getSelectStateProperty().get() == SelectState.LINE || model.getSelectStateProperty().get() == SelectState.ASSOCIATION
                            || model.getSelectStateProperty().get() == SelectState.GENERALIZATION && filteredNode instanceof AnchorPoint) {
                        clickedNodes.add(filteredNode);
                        if (clickedNodes.size() == 2) {
                            UMLLine line;
                            switch (model.getSelectStateProperty().get()) {
                                case ASSOCIATION:
                                    line = new Association((ClassBox) (clickedNodes.get(0)), (ClassBox) (clickedNodes.get(1)));
                                    view.getEditPane().getChildren().add(line);
                                    clickedNodes.clear();
                                    break;

                                case LINE:
                                    line = new UMLArrowLine((ClassBox) (clickedNodes.get(0)), (ClassBox) (clickedNodes.get(1)));
                                    view.getEditPane().getChildren().add(line);
                                    clickedNodes.clear();
                                    break;

                                case GENERALIZATION:
                                    line = new Generalization((ClassBox) (clickedNodes.get(0)), (ClassBox) (clickedNodes.get(1)));
                                    view.getEditPane().getChildren().add(line);
                                    clickedNodes.clear();
                                    break;

                            }

                        }
                    }
                }
            }
        });

        model.getCurrentlySelectedNodeProperty().addListener((ObservableValue<? extends Node> ov,
                Node last_selected, Node new_selected) -> {

                    if (last_selected instanceof ClassBox) {
                       ((ClassBox) last_selected).removeActions();
                    }

                    if (new_selected.getParent() instanceof UMLLine) {
                        new_selected = (UMLLine) new_selected.getParent();
                    }

                    if (new_selected instanceof SelectedPanel) {
                        ((SelectedPanel) new_selected).createAndGeneratePanel(view.getCurrentlySelectedPane());
                    }

                });

        /**
         * almost scrolling because why not
         */
        view.getEditPane().setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                double scaleFactor = (event.getDeltaY() > 0) ? 1.5 : 1 / 1.5;

                if (view.getEditPane().getScaleX() * scaleFactor >= 1.0) {
                    view.getEditPane().setScaleX(view.getEditPane().getScaleX() * scaleFactor);
                    view.getEditPane().setScaleY(view.getEditPane().getScaleY() * scaleFactor);
                }

            }
        });
        
        
        model.getViewStateProperty().addListener((ObservableValue<? extends ViewState> ov,
                ViewState old_state, ViewState new_state) -> {
                    
            switch(new_state){
                case CLASS_UML:
                    view.createUmlClassToggleButtons();
                    view.getCurrentlySelectedPane().getChildren().clear();
                    break;
                case USE_CASE_UML:
                    view.createUmlUseCaseButtons();
                    view.getCurrentlySelectedPane().getChildren().clear();
                    break;
                    
                default:
                    //something went wrong
                    break;
               
            }

                });

    }

    /**
     * return the view
     *
     * @return view
     */
    public UmlView getView() {
        return view;
    }

    /**
     * returns the model
     *
     * @return UmlModel
     */
    public UmlModel getModel() {
        return model;
    }

    /**
     * Checks if what you clicked on is the pane
     *
     * @param n what you clicked on
     * @return the node or null if the pane
     */
    private Node checkIfPane(Node n) {
        if (n.getClass().equals(EditPane.class)) {
            return null;
        } else {
            return n;
        }
    }

    /**
     * sets the panes clicked state to null
     */
    private void setLineState() {
        view.getEditPane().setOnMouseClicked(null);
    }

    /**
     * Sets the panes clicks to create class boxes
     */
    private void setClassBoxState() {
        EventHandler<MouseEvent> createClassBox = (event) -> {
            double x = event.getX();
            double y = event.getY();
            //System.out.println("You created a ClassBox at " + x + " , " + y);
            view.getEditPane().getChildren().add(new ClassBox(x, y));
        };

        view.getEditPane().setOnMouseClicked(createClassBox);
    }

    /**
     * sets the panes clicks to null
     */
    private void setSelectState() {
        view.getEditPane().setOnMouseClicked(null);
    }
}
