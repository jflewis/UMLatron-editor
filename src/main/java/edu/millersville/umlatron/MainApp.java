package edu.millersville.umlatron;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class MainApp extends Application {

   //variables for storing initial position before drag of box
    private double initX;
    private double initY;
    private Point2D dragAnchor;
    final Rectangle rectangle = new Rectangle(750,750,Color.WHITE);
    
    
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 750, 750);
        primaryStage.setTitle("proof of concept uml editor");
        primaryStage.setScene(scene);
        
        final Rectangle box1 = createBox(Color.BLUE, 250, 250);
        final Rectangle box3 = createBox(Color.RED, 250,400);

        box1.setTranslateX(250);
        box1.setTranslateY(250);
        
        root.getChildren().addAll(rectangle,box1,box3);
        
        /*
        rectangle.setOnMouseClicked(new EventHandler<MouseEvent>(){
            
            @Override
            public void handle(MouseEvent me){
                double x = me.getSceneX();
                double y = me.getSceneY();
                System.out.println("You created a box at " + x + " , " + y);
                root.getChildren().add(createBox(Color.GRAY,x,y));
                
                
            }
        
        });
        */
        rectangle.setOnMouseClicked((event) -> {
            double x = event.getSceneX();
            double y = event.getSceneY();
            System.out.println("You created a box at " + x + " , " + y);
            root.getChildren().add(createBox(Color.GRAY,x,y));
        });
        
        primaryStage.show();
    }
    
    /**
     * Temporary method used to create a box on the scene
     * @param name name of the box
     * @param color color of the box
     * @return an instance of a rectangle object
     */
    Rectangle createBox( Color color,double x, double y){
        final Rectangle box = new Rectangle(70,100,color);
        box.setTranslateX(x);
        box.setTranslateY(y);
        box.setArcWidth(20);
        box.setArcHeight(20);
        box.setCursor(Cursor.OPEN_HAND);
        
        box.setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                double dragX = me.getSceneX() - dragAnchor.getX();
                double dragY = me.getSceneY() - dragAnchor.getY();
                //calculate new position of the circle
                double newXPosition = initX + dragX;
                double newYPosition = initY + dragY;
                //if new position do not exceeds borders of the rectangle, translate to this position
                if ((newXPosition>=box.getX()) && (newXPosition<=750-((box.getX()+box.widthProperty().getValue())))) {
                    box.setTranslateX(newXPosition);
                }
                if ((newYPosition>=box.getY()) && (newYPosition<=750-(box.getY()+box.heightProperty().getValue()))){
                    box.setTranslateY(newYPosition);
                }
            }
        });
        
        box.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                 //when mouse is pressed, store initial position
                initX = box.getTranslateX();
                initY = box.getTranslateY();
                dragAnchor = new Point2D(me.getSceneX(), me.getSceneY());
            }
        });
        
        return box;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
