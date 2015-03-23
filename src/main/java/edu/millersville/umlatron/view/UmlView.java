/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.millersville.umlatron.view;

import edu.millersville.umlatron.model.State;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * This class creates and supplies the view for the program.
 * 
 * 
 * @author John Lewis
 */
public class UmlView extends BorderPane {
    private ToggleGroup stateToggle = new ToggleGroup();
    private HBox toggleButtons;
    private HBox currentlySelectedPanel;
    private Pane editPane = new Pane();

    public UmlView(){
        super();
        toggleButtons = createToggleButtons();
        currentlySelectedPanel = createCurrentlySelectedPanel();
        
        this.setCenter(editPane);
       
        this.setTop(createTopPanel(toggleButtons,currentlySelectedPanel));
  
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
        hbox.setSpacing(4);
        Label label = new Label("Currently selected node : filler space ");
               
        Button bt1 = new Button("place holder");
        bt1.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(bt1, Priority.ALWAYS);
        
        Button bt2= new Button("place holder");
        bt2.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(bt2, Priority.ALWAYS);

        hbox.getChildren().addAll(label,bt1,bt2);
        hbox.setStyle("-fx-border-color: black;");
        return hbox;
    }
    
    private VBox createTopPanel(HBox states, HBox selectedPanel)
    {
        VBox panel = new VBox();
        panel.getChildren().addAll(states,selectedPanel);
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
