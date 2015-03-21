package edu.millersville.umlatron;

import edu.millersville.umlatron.view.UMLDottedLine;
import edu.millersville.umlatron.view.UMLLine;
import edu.millersville.umlatron.controller.UmlatronController;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

//I've noticed that all events that use mouse drag break if the mouse is drug too quickly.
//This is most evident in the new resize line methods, but you can also notice it when dragging boxes,
//dragging lines, or creating lines by dragging them.
public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        
        UmlatronController controller = new UmlatronController();
        Scene scene = new Scene(controller.getView(), 950, 750);
        primaryStage.setTitle("proof of concept uml editor");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
