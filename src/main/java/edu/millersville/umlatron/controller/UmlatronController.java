
package edu.millersville.umlatron.controller;

import edu.millersville.umlatron.view.umlRecursiveLines.RecursiveAssociation;
import edu.millersville.umlatron.view.umlRecursiveLines.RecursiveGeneralization;
import edu.millersville.umlatron.view.umlRecursiveLines.UMLRecursiveArrowLine;
import edu.millersville.umlatron.view.umlRecursiveLines.UMLRecursiveLine;
import edu.millersville.umlatron.view.umlLines.Association;
import edu.millersville.umlatron.view.umlLines.Generalization;
import edu.millersville.umlatron.view.umlLines.UMLArrowLine;
import edu.millersville.umlatron.view.umlLines.UMLLine;
import edu.millersville.umlatron.model.*;
import edu.millersville.umlatron.view.*;
import javafx.beans.value.ObservableValue;
import java.util.ArrayList;
import javafx.collections.ListChangeListener.Change;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Toggle;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * The Main Controller.
 * This listens to changes from the model.
 * Handles listening to the clicks on the editpane and depending on 
 * which state the model is in, handles the action according.
 * @author John Lewis
 */
public class UmlatronController {

    UmlModel model = new UmlModel();
    public Stage stage;
    UmlView view;
    ArrayList<Node> clickedNodes = new ArrayList<>();

    public UmlatronController(Stage stage) {

        this.stage = stage;
        this.view = new UmlView(this);
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

                        case USER:
                            setUserState();
                            break;
                            
                        case USE_CASE:
                            setCircleState();
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

                            case USER:
                                model.setSelectState(SelectState.USER);
                                break;
                            
                            case USE_CASE:
                                model.setSelectState(SelectState.USE_CASE);
                                break;

                            default:
                                // something went wrong
                                break;

                        }

                    }
                });
        
        view.getEditPane().getChildren().addListener((Change<? extends Node> changed) ->{
            while(changed.next()){
                if(changed.wasRemoved()){
                    this.clickedNodes.removeAll(changed.getRemoved());
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
                    if ((model.getSelectStateProperty().get() == SelectState.LINE || model.getSelectStateProperty().get() == SelectState.ASSOCIATION
                            || model.getSelectStateProperty().get() == SelectState.GENERALIZATION) && filteredNode instanceof AnchorPoint) {
                        clickedNodes.add(filteredNode);
                        if (clickedNodes.size() == 2) {
                            UMLLine line;
                            switch (model.getSelectStateProperty().get()) {
                                case ASSOCIATION:
                                    if (clickedNodes.get(0) != clickedNodes.get(1)) {
                                        line = new Association((AnchorPoint) (clickedNodes.get(0)), (AnchorPoint) (clickedNodes.get(1)));
                                        view.getEditPane().getChildren().add(line);
                                        clickedNodes.clear();
                                    } else {
                                        view.getEditPane().getChildren().add(new RecursiveAssociation((AnchorPoint) clickedNodes.get(0)));
                                        clickedNodes.clear();
                                    }
                                    model.projectSaved = false;
                                    break;

                                case LINE:
                                    if (clickedNodes.get(0) != clickedNodes.get(1)) {
                                        line = new UMLArrowLine((AnchorPoint) (clickedNodes.get(0)), (AnchorPoint) (clickedNodes.get(1)));
                                        view.getEditPane().getChildren().add(line);
                                        clickedNodes.clear();
                                    } else {
                                        view.getEditPane().getChildren().add(new UMLRecursiveArrowLine((AnchorPoint) clickedNodes.get(0)));
                                        clickedNodes.clear();
                                    }
                                    model.projectSaved = false;
                                    break;

                                case GENERALIZATION:
                                    if (clickedNodes.get(0) != clickedNodes.get(1)) {
                                        line = new Generalization((AnchorPoint) (clickedNodes.get(0)), (AnchorPoint) (clickedNodes.get(1)));
                                        view.getEditPane().getChildren().add(line);
                                        clickedNodes.clear();
                                    } else {
                                        view.getEditPane().getChildren().add(new RecursiveGeneralization((AnchorPoint) clickedNodes.get(0)));
                                        clickedNodes.clear();
                                    }
                                    model.projectSaved = false;
                                    break;

                            }

                        }
                    }
                }
            }
        });

        model.getCurrentlySelectedNodeProperty().addListener((ObservableValue<? extends Node> ov,
                Node last_selected, Node new_selected) -> {

                    if (last_selected != null) {
                        //do stuff
                        if (last_selected instanceof ClassBox) {
                            ((ClassBox) last_selected).removeActions();
                        }
                        if (last_selected instanceof User) {
                            ((User) last_selected).removeActions();
                        }
                        if (last_selected instanceof UseCase) {
                            ((UseCase) last_selected).removeActions();
                        }
                        if (last_selected.getParent() instanceof UMLLine) {
                            last_selected = (UMLLine) last_selected.getParent();
                            ((UMLLine) last_selected).removeSelection();
                        }
                        if (new_selected instanceof ClassBox) {
                            ((ClassBox) new_selected).applySelection();
                        }

                        if (new_selected.getParent() instanceof UMLLine) {
                            new_selected = (UMLLine) new_selected.getParent();
                            ((UMLLine) new_selected).applySelection();
                        }
                    }

                    if (new_selected.getParent() instanceof UMLRecursiveLine) {
                        new_selected = (UMLRecursiveLine) new_selected.getParent();
                    }

                    if (new_selected instanceof SelectedPanel) {
                        ((SelectedPanel) new_selected).createAndGeneratePanel(view.getCurrentlySelectedPane());
                    }

                });

     
        model.getViewStateProperty().addListener((ObservableValue<? extends ViewState> ov,
                ViewState old_state, ViewState new_state) -> {

                    switch (new_state) {
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
     * Return the view.
     *
     * @return view
     */
    public UmlView getView() {
        return view;
    }

    /**
     * Returns the model.
     *
     * @return UmlModel
     */
    public UmlModel getModel() {
        return model;
    }

    /**
     * Checks if what you clicked on is the pane.
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
     * Sets the panes clicked state to null.
     * This ensures that all clicks will not effect the editpane.s
     */
    private void setLineState() {
        view.getEditPane().setOnMouseClicked(null);
    }

    /**
     * Sets clicks on the editpane to create ClassBoxes.
     */
    private void setClassBoxState() {
        EventHandler<MouseEvent> createClassBox = (event) -> {
            double x = event.getX();
            double y = event.getY();
            //System.out.println("You created a ClassBox at " + x + " , " + y);
            view.getEditPane().getChildren().add(new ClassBox(x, y));
            model.projectSaved = false;

        };

        view.getEditPane().setOnMouseClicked(createClassBox);
    }

    /**
     * Sets clicks on the editpane to create Users.
     */
    private void setUserState() {
        EventHandler<MouseEvent> createUser = (event) -> {
            double x = event.getX();
            double y = event.getY();
            //System.out.println("You created a ClassBox at " + x + " , " + y);
            view.getEditPane().getChildren().add(new User(x, y));
            model.projectSaved = false;

        };

        view.getEditPane().setOnMouseClicked(createUser);
    }
    
     /**
     * Sets clicks on the editpane to create use cases.
     */
    private void setCircleState() {
        EventHandler<MouseEvent> createCircle = (event) -> {
            double x = event.getX();
            double y = event.getY();
            //System.out.println("You created a ClassBox at " + x + " , " + y);
            view.getEditPane().getChildren().add(new UseCase(x, y));
            model.projectSaved = false;

        };

        view.getEditPane().setOnMouseClicked(createCircle);
    }

    /**
     * Sets the panes clicks to null.
     * Will ensure that when the user is in the select state clicks will not effect anything
     */
    private void setSelectState() {
        view.getEditPane().setOnMouseClicked(null);
    }
}
