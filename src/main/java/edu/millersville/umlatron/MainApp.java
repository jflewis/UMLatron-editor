package edu.millersville.umlatron;


import edu.millersville.umlatron.controller.UmlatronController;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        
        UmlatronController controller = new UmlatronController(primaryStage);
        Scene scene = new Scene(controller.getView(), 950, 750);
        scene.getStylesheets().add("/styles/Buttons.css");
        primaryStage.setTitle("UML'atron");
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
