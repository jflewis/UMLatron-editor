/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.millersville.umlatron.view;

import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import edu.millersville.umlatron.model.State;
import javafx.geometry.Insets;
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
import javafx.scene.shape.Polyline;

/**
 * This class creates and supplies the view for the program.
 * 
 * 
 * @author John Lewis
 */
public class UmlView extends BorderPane {
    private ToggleGroup stateToggle = new ToggleGroup();
    private MenuBar mainApp;
    private HBox toggleButtons;
    private HBox currentlySelectedPanel;
    private Pane editPane = new Pane();

    public UmlView(){
        super();
        mainApp = applicationBar();
        toggleButtons = createToggleButtons();
        currentlySelectedPanel = createCurrentlySelectedPanel();
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        editPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setCenter(editPane);
        this.setTop(createTopPanel(mainApp, toggleButtons,currentlySelectedPanel));
  
    }
    private MenuBar applicationBar(){
    	
    	MenuBar menuBar = new MenuBar();
        menuBar.useSystemMenuBarProperty().set(true); //if it's mac put's it up top

        menuBar.getStylesheets().add("/styles/MenuBar.css");
        
        Menu menuFile = new Menu("File");
        //Menu menuTemp2 = new Menu("TempField2");
        
        MenuItem newThing = new MenuItem("New");
        //currently does nothing
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction((event) -> {
        		System.exit(0);
        });
        
        menuBar.getMenus().addAll(menuFile);
        menuBar.prefWidthProperty().bind(editPane.widthProperty());
        menuBar.setStyle("-fx-padding: 2 2 2 5;");
        
        menuFile.getItems().addAll(newThing, new SeparatorMenuItem(), exit);
        
        return menuBar;
    	
    	
    	
    }
    
    private HBox createToggleButtons(){
        HBox hbox = new HBox();
        
        ToggleButton tb1 = new ToggleButton("Select");
        tb1.setUserData(State.SELECT);
        tb1.setToggleGroup(stateToggle);
        tb1.setSelected(true);
        tb1.setMaxWidth(Double.MAX_VALUE);
        
        
        ToggleButton tb2 = new ToggleButton("ClassBox");
        tb2.setUserData(State.CLASSBOX);
        tb2.setToggleGroup(stateToggle);
        tb2.setMaxWidth(Double.MAX_VALUE);
        
        ToggleButton tb3 = new ToggleButton("Line");
        tb3.setUserData(State.LINE);
        tb3.setToggleGroup(stateToggle);
        tb3.setMaxWidth(Double.MAX_VALUE);
        
        ToggleButton tb4 = new ToggleButton("Association");
        tb4.setUserData(State.ASSOCIATION);
        tb4.setToggleGroup(stateToggle);
        tb4.setMaxWidth(Double.MAX_VALUE);
        
        hbox.getChildren().add(tb1);
        hbox.getChildren().add(tb2);
        hbox.getChildren().add(tb3);
        hbox.getChildren().add(tb4);
        HBox.setHgrow(tb1, Priority.ALWAYS);
        HBox.setHgrow(tb2, Priority.ALWAYS);
        HBox.setHgrow(tb3, Priority.ALWAYS);
        HBox.setHgrow(tb4, Priority.ALWAYS);
        
        return hbox;
        
    }
    
    private HBox createCurrentlySelectedPanel(){
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(4, 12, 4, 12));
        hbox.setSpacing(10);

        return hbox;
    }
    
    private VBox createTopPanel(MenuBar mainApp, HBox states, HBox selectedPanel)
    {
        VBox panel = new VBox();
        panel.getChildren().addAll(mainApp, states,selectedPanel);
        return panel;
    }
    
    
    public ToggleGroup getStateToggle(){
        return stateToggle;
    }
    
    public HBox getCurrentlySelectedPane(){
        return currentlySelectedPanel;
    }
    
    public Pane getEditPane(){
        return editPane;
    }
}
