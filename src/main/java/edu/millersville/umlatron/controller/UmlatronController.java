/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.millersville.umlatron.controller;

import edu.millersville.umlatron.view.Box;
import edu.millersville.umlatron.model.UmlModel;
import edu.millersville.umlatron.view.UmlView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;


/**
 *
 * @author John Lewis
 */
public class UmlatronController {
    
    UmlModel model = new UmlModel();
    UmlView view = new UmlView();
    
    
    public UmlatronController(){
        
        view.getEditPane().setOnMouseClicked((event) -> {
        if (event.getPickResult().getIntersectedNode() != null) {
                double x = event.getX();
                double y = event.getY();
                System.out.println("You created a box at " + x + " , " + y);
                view.getEditPane().getChildren().add(new Box(Color.GRAY, x, y));
            }
        
        });
       
    
    }
    
    public BorderPane getView(){
        return view;
    }
    
    
}
/**
 * Group root = new Group();
        Scene scene = new Scene(controller.getView(), 750, 750);
        primaryStage.setTitle("proof of concept uml editor");
        primaryStage.setScene(scene);

  
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
 */
