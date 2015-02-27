package edu.millersville.umlatron;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
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

//I've noticed that all events that use mouse drag break if the mouse is drug too quickly.
//This is most evident in the new resize line methods, but you can also notice it when dragging boxes,
//dragging lines, or creating lines by dragging them.
public class MainApp extends Application {

    //I needed this because I was unsure how to grab the most recent line from root
    private UMLLine lines;
    private UMLDottedLine dottedLines;

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 750, 750);
        primaryStage.setTitle("proof of concept uml editor");
        primaryStage.setScene(scene);

        final Rectangle box1 = new Box(Color.BLUE, 250, 250);
        final Rectangle box3 = new Box(Color.RED, 250,400);
  
        EventHandler<MouseEvent> createBox = (event) -> {
            //only place if not on a node 
            if (event.getPickResult().getIntersectedNode() == null) {
                double x = event.getSceneX();
                double y = event.getSceneY();
                System.out.println("You created a box at " + x + " , " + y);
                root.getChildren().add(new Box(Color.GRAY, x, y));
            }
        };

        EventHandler<MouseEvent> drawLine = (event) -> {
            double x = event.getSceneX();
            double y = event.getSceneY();
            System.out.println(event.getEventType());
            System.out.println("You created a line starting at " + x + " , " + y);
            lines = new UMLLine(x, y, x, y);
            root.getChildren().add(lines);
        };

        EventHandler<MouseEvent> updateLine = (event) -> {
            double x = event.getSceneX();
            double y = event.getSceneY();
            lines.setEndX(x);
            lines.setEndY(y);
        };

        EventHandler<MouseEvent> drawDottedLine = (event) -> {
            double x = event.getSceneX();
            double y = event.getSceneY();
            System.out.println(event.getEventType());
            System.out.println("You created a dotted line starting at " + x + " , " + y);
            dottedLines = new UMLDottedLine(x, y, x, y);
            root.getChildren().add(dottedLines);
        };

        EventHandler<MouseEvent> updateDottedLine = (event) -> {
            double x = event.getSceneX();
            double y = event.getSceneY();
            dottedLines.setEndX(x);
            dottedLines.setEndY(y);
        };


        EventHandler<MouseEvent> createClassBox = (event) -> {
            double x = event.getSceneX();
            double y = event.getSceneY();
            System.out.println("You created a ClassBox at " + x + " , " + y);
            root.getChildren().add(new ClassBox(x,y));

        };
        EventHandler<MouseEvent> createDiamndLine = (event) -> {
            //only place if not on a node 
            if (event.getPickResult().getIntersectedNode() == null) {
                double x = event.getSceneX();
                double y = event.getSceneY();
                System.out.println("You created a diamandLine at " + x + " , " + y);
                root.getChildren().add(new DiamondLine(x, y));
            }

        };

        // Menu items -----------------------------------
        MenuItem itemCreateBox = new MenuItem("Boxes");
        itemCreateBox.setOnAction((event) -> {
            scene.setOnMousePressed(null);
            scene.setOnMouseReleased(null);
            scene.setOnMouseDragged(null);
            scene.setOnMouseClicked(createBox);
        });

        MenuItem itemDiamandLine = new MenuItem("DimandLine");
        itemDiamandLine.setOnAction((event) -> {
            scene.setOnMousePressed(null);
            scene.setOnMouseReleased(null);
            scene.setOnMouseDragged(null);
            scene.setOnMouseClicked(createDiamndLine);
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

        MenuItem itemCreateClassBox = new MenuItem("ClassBox");
        itemCreateClassBox.setOnAction((event) -> {
            scene.setOnMousePressed(null);
            scene.setOnMouseReleased(null);
            scene.setOnMouseDragged(null);
            scene.setOnMouseClicked(createClassBox);
           
        });
        
        
        Menu menu = new Menu("Actions");

        menu.getItems().addAll(itemCreateBox, itemCreateLine, itemCreateDottedLine, itemDiamandLine, itemCreateClassBox);

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
