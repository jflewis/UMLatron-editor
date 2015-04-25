package edu.millersville.umlatron;


import edu.millersville.umlatron.controller.UmlatronController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;



public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        
        UmlatronController controller = new UmlatronController(primaryStage);
        
        /*Image applicationIcon = new Image("/images/Decepticon16.png");
        Image applicationIcon64 = new Image("/images/Voltron64.png");
        Image applicationIcon16 = new Image("/images/Voltron16.png");
        Image applicationIcon32 = new Image("/images/Voltron32.png");
        Image applicationIcon128 = new Image("/images/Voltron128.png");
        Image applicationIcon256 = new Image("/images/Voltron256.png");
        Image applicationIcon512 = new Image("/images/Voltron512.png");
        */
       primaryStage.getIcons().add(new Image("/images/Voltron16.png"));
        primaryStage.getIcons().add(new Image("/images/Voltron32.png"));
        primaryStage.getIcons().add(new Image("/images/Voltron64.png"));
        primaryStage.getIcons().add(new Image("/images/Voltron128.png"));
        primaryStage.getIcons().add(new Image("/images/Voltron256.png"));
        primaryStage.getIcons().add(new Image("/images/Voltron512.png"));
        Scene scene = new Scene(controller.getView(), 950, 750);
        scene.getStylesheets().add("/styles/Buttons.css");
        primaryStage.setTitle("UML'atron");
        primaryStage.setScene(scene);
        primaryStage.show();
       
    }
    
    public static void main(String[] args) {
        
        launch(args);
    }

}
