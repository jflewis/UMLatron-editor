package edu.millersville.umlatron;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;


public class MainApp extends Application {

    
  
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 750, 750);
        primaryStage.setTitle("proof of concept uml editor");
        primaryStage.setScene(scene);
        
        

        final Rectangle box1 = new Box(Color.BLUE, 250, 250);
        final Rectangle box3 = new Box(Color.RED, 250,400);

        
         
         //I needed this because I was unsure how to grab the most recent line from root
         ArrayList<UMLLine> lines;
         lines = new ArrayList<UMLLine>();
         ArrayList<UMLDottedLine> dottedLines;
         dottedLines = new ArrayList<UMLDottedLine>();
        
        EventHandler<MouseEvent> createBox = (event) -> {
            double x = event.getSceneX();
            double y = event.getSceneY();
            System.out.println("You created a box at " + x + " , " + y);
            root.getChildren().add(new Box(Color.GRAY,x,y));
        };
        
        EventHandler<MouseEvent> drawLine = (event) -> {
        	double x = event.getSceneX();
        	double y = event.getSceneY();
            System.out.println(event.getEventType());
            System.out.println("You created a line starting at " + x + " , " + y);
            lines.add(new UMLLine(x,y,x,y));
            root.getChildren().add(lines.get(lines.size() - 1));
        };
        
        EventHandler<MouseEvent> updateLine = (event) -> {
        	double x = event.getSceneX();
            double y = event.getSceneY();
            lines.get(lines.size() - 1).setEndX(x);
            lines.get(lines.size() - 1).setEndY(y);
        };

        EventHandler<MouseEvent> drawDottedLine = (event) -> {
        	double x = event.getSceneX();
        	double y = event.getSceneY();
            System.out.println(event.getEventType());
            System.out.println("You created a line starting at " + x + " , " + y);
            dottedLines.add(new UMLDottedLine(x,y,x,y));
            root.getChildren().add(dottedLines.get(dottedLines.size() - 1));    
        };

        EventHandler<MouseEvent> updateDottedLine = (event) -> {
        	double x = event.getSceneX();
            double y = event.getSceneY();
            dottedLines.get(dottedLines.size() - 1).setEndX(x);
            dottedLines.get(dottedLines.size() - 1).setEndY(y);
        };
        
        
        
        // Menu items -----------------------------------
        MenuItem itemCreateBox = new MenuItem("Boxes");
        itemCreateBox.setOnAction((event) -> {
            scene.setOnMousePressed(null);
            scene.setOnMouseReleased(null);
            scene.setOnMouseDragged(null);
            scene.setOnMouseClicked(createBox);
           
        });
        
        MenuItem itemCreateDottedLine = new MenuItem("Dotted Lines");
        itemCreateDottedLine.setOnAction((event) -> {
            scene.setOnMouseClicked(null);
            scene.setOnMousePressed(drawDottedLine);
            scene.setOnMouseDragged(updateDottedLine);
            scene.setOnMouseReleased(updateDottedLine);

        });
        
        MenuItem itemCreateLine = new MenuItem("Lines");
        itemCreateLine.setOnAction((event) -> {
            scene.setOnMouseClicked(null);
            scene.setOnMousePressed(drawLine);        
            scene.setOnMouseDragged(updateLine);
            scene.setOnMouseReleased(updateLine);

        });
        
        Menu menu = new Menu("Actions");
        menu.getItems().addAll(itemCreateBox,itemCreateLine, itemCreateDottedLine);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);
        
        //---------------------------------------------------
        
        
        scene.setOnMouseClicked(createBox);
        root.getChildren().addAll(menuBar);
        primaryStage.show();
    }
    
   

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
