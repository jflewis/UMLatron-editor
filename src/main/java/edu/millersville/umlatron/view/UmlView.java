
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
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
 * @author John Lewis
 */
public class UmlView extends BorderPane {

    private final ToggleGroup stateToggle = new ToggleGroup();
    private final  MenuBar mainApp;
    private final HBox toggleButtons = new HBox();
    private final  HBox currentlySelectedPanel;
    private final EditPane editPane = new EditPane();
    private final UmlatronController controller;
    private final FileOperations fileOps;

    /**
     * Class constructor 
     * @param controller The UMLController passed to the constructor through the controller
     */
    public UmlView(UmlatronController controller) {
    	
        super();
        this.controller = controller;
        this.fileOps = new FileOperations(editPane, controller);
        mainApp = applicationBar();
        createUmlClassToggleButtons();
        editPane.setId("pane");
        currentlySelectedPanel = createCurrentlySelectedPanel();
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        editPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setCenter(editPane);
        this.setTop(createTopPanel(mainApp, toggleButtons, currentlySelectedPanel));
    }

    /**
     * A private method to initialize the application bar of the view
     * @return A menu bar containing menuitems
     */
    private MenuBar applicationBar() {

        MenuBar menuBar = new MenuBar();
        menuBar.useSystemMenuBarProperty().set(true); //if it's mac put's it up top
        menuBar.getStylesheets().add("/styles/MenuBar.css");

        Menu fileOperations = new Menu("File");
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
            if (controller.getModel().projectSaved == false) {
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
            } else {
                editPane.getChildren().clear();
                fileOps.open();
            }

        });
        MenuItem newItem = new MenuItem("New");
        newItem.setOnAction((event) -> {
            if (controller.getModel().projectSaved == false) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Unsaved work");
                alert.setHeaderText("Whoa hold up bud, seems like you have some unsaved work");
                alert.setContentText("Do you want to save it");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    fileOps.save();
                    editPane.getChildren().clear();
                } else {
                    editPane.getChildren().clear();
                }
            } else {
                editPane.getChildren().clear();
            }

        });
        fileOperations.getItems().addAll(newItem, saveButton, load, new SeparatorMenuItem(), exit);

        Menu views = new Menu("Views");
        MenuItem umlClass = new MenuItem("Class diagrahm");
        umlClass.setOnAction((event) -> {
            if (controller.getModel().getViewStateProperty().get() == ViewState.CLASS_UML) {
                //do nothing
            } else {
                if (controller.getModel().projectSaved == false) {
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Unsaved work");
                    alert.setHeaderText("Whoa hold up bud, seems like you have some unsaved work");
                    alert.setContentText("Do you want to save it");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        fileOps.save();
                        editPane.getChildren().clear();
                        controller.getModel().setViewState(ViewState.CLASS_UML);
                    } else {
                        editPane.getChildren().clear();
                        controller.getModel().setViewState(ViewState.CLASS_UML);
                    }
                } else {
                    editPane.getChildren().clear();
                    controller.getModel().setViewState(ViewState.CLASS_UML);

                }
            }
        });
        MenuItem useCase = new MenuItem("Use case diagrahm");
        useCase.setOnAction((event) -> {
            if (controller.getModel().getViewStateProperty().get() == ViewState.USE_CASE_UML) {
                //do nothing
            } else {
                if (controller.getModel().projectSaved == false) {
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Unsaved work");
                    alert.setHeaderText("Whoa hold up bud, seems like you have some unsaved work");
                    alert.setContentText("Do you want to save it");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        fileOps.save();
                        editPane.getChildren().clear();
                        controller.getModel().setViewState(ViewState.USE_CASE_UML);
                    } else {
                        editPane.getChildren().clear();
                        controller.getModel().setViewState(ViewState.USE_CASE_UML);
                    }
                } else {
                    editPane.getChildren().clear();
                    controller.getModel().setViewState(ViewState.USE_CASE_UML);
                }
            }
        });
        views.getItems().addAll(umlClass, new SeparatorMenuItem(), useCase);

        menuBar.getMenus().addAll(fileOperations, views);
        menuBar.prefWidthProperty().bind(editPane.widthProperty());
        menuBar.setStyle("-fx-padding: 2 2 2 5;");

        return menuBar;

    }

    /**
     * Creates the menu bars for the view state Class uml.
     * Toggle buttons are created and placed in a toggle group
     * to be listened to by the controller to change the selected state.
     * This is the default toggle buttons on first load of program
     */
    final public void createUmlClassToggleButtons() {
        stateToggle.getToggles().clear();
        toggleButtons.getChildren().clear();

        ToggleButton tb1 = new ToggleButton("Select  ");
        Image selectImg = new Image("/images/Select.png");
        ImageView iv1 = new ImageView();
        iv1.setImage(selectImg);
        tb1.setContentDisplay(ContentDisplay.RIGHT);
        tb1.setGraphic(new ImageView(selectImg));
        tb1.setUserData(SelectState.SELECT);
        tb1.setToggleGroup(stateToggle);
        tb1.setSelected(true);
        tb1.setMaxWidth(Double.MAX_VALUE);

        ToggleButton tb2 = new ToggleButton("ClassBox  ");
        Image cBox = new Image("/images/PlainClassBox.png");
        ImageView iv2 = new ImageView();
        iv2.setImage(cBox);
        tb2.setContentDisplay(ContentDisplay.RIGHT);
        tb2.setGraphic(new ImageView(cBox));
        tb2.setUserData(SelectState.CLASSBOX);
        tb2.setToggleGroup(stateToggle);
        tb2.setMaxWidth(Double.MAX_VALUE);

        ToggleButton tb3 = new ToggleButton("Line  ");
        Image lineImg = new Image("/images/SolidLine.png");
        ImageView iv3 = new ImageView();
        iv3.setImage(lineImg);
        tb3.setContentDisplay(ContentDisplay.RIGHT);
        tb3.setGraphic(new ImageView(lineImg));
        tb3.setUserData(SelectState.LINE);
        tb3.setToggleGroup(stateToggle);
        tb3.setMaxWidth(Double.MAX_VALUE);

        ToggleButton tb4 = new ToggleButton("Association  ");
        Image assImg = new Image("/images/Association.png");
        ImageView iv4 = new ImageView();
        iv4.setImage(assImg);
        tb4.setContentDisplay(ContentDisplay.RIGHT);
        tb4.setGraphic(new ImageView(assImg));
        tb4.setUserData(SelectState.ASSOCIATION);
        tb4.setToggleGroup(stateToggle);
        tb4.setMaxWidth(Double.MAX_VALUE);

        ToggleButton tb5 = new ToggleButton("Generalization  ");
        Image genImg = new Image("/images/Generelization.png");
        ImageView iv5 = new ImageView();
        iv5.setImage(genImg);
        tb5.setContentDisplay(ContentDisplay.RIGHT);
        tb5.setGraphic(new ImageView(genImg));
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

    /**
     * Creates the toggle buttons for the Use case view.
     * Toggle buttons are created and placed in a toggle group
     * to be listened to by the controller to change the selected state.
     */
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
        tb3.setUserData(SelectState.USE_CASE);
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

    /**
     * Creates the HBox for the currently selected node panel.
     * @return An Empty HBox. This HBox is filled when a node is selected.
     */
    private HBox createCurrentlySelectedPanel() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(4, 12, 4, 12));
        hbox.setSpacing(10);
        hbox.setPrefHeight(40);
        return hbox;
    }

    /**
     * A helper function to clear the pane of all nodes.
     */
    public void clearPane() {
        editPane.getChildren().clear();
    }

    /**
     * Private helper method to initialize the top panels in the view.
     * @param mainApp The menu Bar 
     * @param states The toggle buttons
     * @param selectedPanel The currently selected panel
     * @return 
     */
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
