/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.millersville.umlatron.view;

import edu.millersville.umlatron.Util.FileOperations;
import edu.millersville.umlatron.controller.UmlatronController;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import edu.millersville.umlatron.model.SelectState;
import edu.millersville.umlatron.model.ViewState;
import java.util.Optional;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * This class creates and supplies the view for the program.
 *
 *
 * @author John Lewis
 */
public class UmlView extends BorderPane {

    private ToggleGroup stateToggle = new ToggleGroup();
    private MenuBar mainApp;
    private HBox toggleButtons = new HBox();
    private HBox currentlySelectedPanel;
    private EditPane editPane = new EditPane();
    private UmlatronController controller;
    private FileOperations fileOps;

    public UmlView(UmlatronController controller) {
        super();
        this.controller = controller;
        this.fileOps = new FileOperations(editPane, controller.stage);
        mainApp = applicationBar();
        createUmlClassToggleButtons();
        currentlySelectedPanel = createCurrentlySelectedPanel();
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        editPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setCenter(editPane);
        this.setTop(createTopPanel(mainApp, toggleButtons, currentlySelectedPanel));
    }

    private MenuBar applicationBar() {

        MenuBar menuBar = new MenuBar();
        menuBar.useSystemMenuBarProperty().set(true); //if it's mac put's it up top
        menuBar.getStylesheets().add("/styles/MenuBar.css");

        Menu fileOperations = new Menu("File");
        MenuItem newThing = new MenuItem("New");
        MenuItem saveButton = new MenuItem("Save");
        saveButton.setOnAction((event) -> {
            fileOps.save();
            controller.getModel().projectSaved = true;
        });
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction((event) -> {
            System.exit(0);
        });
        MenuItem load = new MenuItem("Open");
        load.setOnAction((event) -> {
            if (controller.getModel().projectSaved == false){
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Unsaved work");
                alert.setHeaderText("Whoa hold up bud, seems like you have some unsaved work");
                alert.setContentText("Do you want to save it");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    fileOps.save();
                    editPane.getChildren().clear();
                    fileOps.open();
                } else {
                    editPane.getChildren().clear();
                    fileOps.open();
                }
            }else{
                editPane.getChildren().clear();
                fileOps.open();
            }
            
        });
        fileOperations.getItems().addAll(newThing, new SeparatorMenuItem(), saveButton, load, new SeparatorMenuItem(), exit);

        Menu views = new Menu("Views");
        MenuItem umlClass = new MenuItem("Class diagrahm");
        umlClass.setOnAction((event) -> {
            if (controller.getModel().getViewStateProperty().get() == ViewState.CLASS_UML) {
                //do nothing
            } else {
                editPane.getChildren().clear();
                controller.getModel().setViewState(ViewState.CLASS_UML);
            }
        });
        MenuItem useCase = new MenuItem("Use case diagrahm");
        useCase.setOnAction((event) -> {
            if (controller.getModel().getViewStateProperty().get() == ViewState.USE_CASE_UML) {
                //do nothing
            } else {
                editPane.getChildren().clear();
                controller.getModel().setViewState(ViewState.USE_CASE_UML);
            }
        });
        views.getItems().addAll(umlClass, new SeparatorMenuItem(), useCase);

        menuBar.getMenus().addAll(fileOperations, views);
        menuBar.prefWidthProperty().bind(editPane.widthProperty());
        menuBar.setStyle("-fx-padding: 2 2 2 5;");

        return menuBar;

    }

    final public void createUmlClassToggleButtons() {
        stateToggle.getToggles().clear();
        toggleButtons.getChildren().clear();

        ToggleButton tb1 = new ToggleButton("Select");
        tb1.setUserData(SelectState.SELECT);
        tb1.setToggleGroup(stateToggle);
        tb1.setSelected(true);
        tb1.setMaxWidth(Double.MAX_VALUE);

        ToggleButton tb2 = new ToggleButton("ClassBox");
        tb2.setUserData(SelectState.CLASSBOX);
        tb2.setToggleGroup(stateToggle);
        tb2.setMaxWidth(Double.MAX_VALUE);

        ToggleButton tb3 = new ToggleButton("Line");
        tb3.setUserData(SelectState.LINE);
        tb3.setToggleGroup(stateToggle);
        tb3.setMaxWidth(Double.MAX_VALUE);

        ToggleButton tb4 = new ToggleButton("Association");
        tb4.setUserData(SelectState.ASSOCIATION);
        tb4.setToggleGroup(stateToggle);
        tb4.setMaxWidth(Double.MAX_VALUE);

        ToggleButton tb5 = new ToggleButton("Generalization");
        tb5.setUserData(SelectState.GENERALIZATION);
        tb5.setToggleGroup(stateToggle);
        tb5.setMaxWidth(Double.MAX_VALUE);

        toggleButtons.getChildren().add(tb1);
        toggleButtons.getChildren().add(tb2);
        toggleButtons.getChildren().add(tb3);
        toggleButtons.getChildren().add(tb4);
        toggleButtons.getChildren().add(tb5);
        HBox.setHgrow(tb1, Priority.ALWAYS);
        HBox.setHgrow(tb2, Priority.ALWAYS);
        HBox.setHgrow(tb3, Priority.ALWAYS);
        HBox.setHgrow(tb4, Priority.ALWAYS);
        HBox.setHgrow(tb5, Priority.ALWAYS);

    }

    final public void createUmlUseCaseButtons() {
        stateToggle.getToggles().clear();
        toggleButtons.getChildren().clear();

        ToggleButton tb1 = new ToggleButton("Select");
        tb1.setUserData(SelectState.SELECT);
        tb1.setToggleGroup(stateToggle);
        tb1.setSelected(true);
        tb1.setMaxWidth(Double.MAX_VALUE);

        ToggleButton tb2 = new ToggleButton("User");
        tb2.setUserData(SelectState.USER);
        tb2.setToggleGroup(stateToggle);
        tb2.setSelected(true);
        tb2.setMaxWidth(Double.MAX_VALUE);

        ToggleButton tb3 = new ToggleButton("Use Case");
        tb3.setUserData(SelectState.CIRCLE);
        tb3.setToggleGroup(stateToggle);
        tb3.setMaxWidth(Double.MAX_VALUE);

        ToggleButton tb4 = new ToggleButton("Line");
        tb4.setUserData(SelectState.LINE);
        tb4.setToggleGroup(stateToggle);
        tb4.setMaxWidth(Double.MAX_VALUE);

        toggleButtons.getChildren().add(tb1);
        toggleButtons.getChildren().add(tb2);
        toggleButtons.getChildren().add(tb3);
        toggleButtons.getChildren().add(tb4);
        HBox.setHgrow(tb1, Priority.ALWAYS);
        HBox.setHgrow(tb2, Priority.ALWAYS);
        HBox.setHgrow(tb3, Priority.ALWAYS);
        HBox.setHgrow(tb4, Priority.ALWAYS);
    }

    private HBox createCurrentlySelectedPanel() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(4, 12, 4, 12));
        hbox.setSpacing(10);
        hbox.setPrefHeight(40);
        return hbox;
    }

    public void clearPane() {
        editPane.getChildren().clear();
    }

    private VBox createTopPanel(MenuBar mainApp, HBox states, HBox selectedPanel) {
        VBox panel = new VBox();
        panel.getChildren().addAll(mainApp, states, selectedPanel);
        return panel;
    }

    public ToggleGroup getStateToggle() {
        return stateToggle;
    }

    public HBox getCurrentlySelectedPane() {
        return currentlySelectedPanel;
    }

    public Pane getEditPane() {
        return editPane;
    }

    public HBox getToggleButtons() {
        return toggleButtons;
    }
}
