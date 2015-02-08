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

    
   //variables for storing initial position before drag of box
   // private double initX;
    //private double initY;
    //private Point2D dragAnchor;
    final Rectangle rectangle = new Rectangle(750,750,Color.WHITE);
    
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 750, 750);
        primaryStage.setTitle("proof of concept uml editor");
        primaryStage.setScene(scene);
        
        
        //final Rectangle box1 = new Box().buildBox(Color.BLUE, 250, 250);
        //final Rectangle box3 = new Box().buildBox(Color.RED, 250,400);
        final Rectangle box1 = new Box(Color.BLUE, 250, 250);
         final Rectangle box3 = new Box(Color.RED, 250,400);
       // box1.setTranslateX(250);
       // box1.setTranslateY(250);
        
         
         //added by Matt
         //I needed this because I was unsure how to grab the most recent line from root
         ArrayList<UMLLine> lines;
         lines = new ArrayList<UMLLine>();
         //end of stuff added by Matt
        
        EventHandler<MouseEvent> createBox = (event) -> {
            double x = event.getSceneX();
            double y = event.getSceneY();
            System.out.println("You created a box at " + x + " , " + y);
            root.getChildren().add(new Box(Color.GRAY,x,y));
        };
        
        EventHandler<MouseEvent> drawLine = (event) -> {
        	//added stuff by Matt
        	double x = event.getSceneX();
        	double y = event.getSceneY();
            System.out.println(event.getEventType());
            System.out.println("You created a line starting at " + x + " , " + y);
            lines.add(new UMLLine(x,y,x,y));
            root.getChildren().add(lines.get(lines.size() - 1));
            //end of stuff added by Matt
        };
        
        //added stuff by Matt
        EventHandler<MouseEvent> updateLine = (event) -> {
        	double x = event.getSceneX();
            double y = event.getSceneY();
            lines.get(lines.size() - 1).setEndX(x);
            lines.get(lines.size() - 1).setEndY(y);
        };
        //end of stuff added by Matt
        
        // Menu items -----------------------------------
        MenuItem itemCreateBox = new MenuItem("Boxes");
        itemCreateBox.setOnAction((event) -> {
            rectangle.setOnMouseClicked(createBox);
        });
        
        MenuItem itemCreateLine = new MenuItem("Lines");
        itemCreateLine.setOnAction((event) -> {
            rectangle.setOnMousePressed(drawLine);
            
            //added stuff
            rectangle.setOnMouseDragged(updateLine);
            rectangle.setOnMouseReleased(updateLine);
            //end of added stuff
            
            System.out.println( event.getEventType()+ "event changed on click");
        });
        
        Menu menu = new Menu("Actions");
        menu.getItems().addAll(itemCreateBox,itemCreateLine);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);
        
        //---------------------------------------------------
        
        
        rectangle.setOnMouseClicked(createBox);
        //changed this to have line2
        root.getChildren().addAll(rectangle,box1,box3,menuBar);
        primaryStage.show();
    }
    
   

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
