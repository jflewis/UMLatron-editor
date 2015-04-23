/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.millersville.umlatron.view.umlLines;

import edu.millersville.umlatron.view.ClassBox;
import edu.millersville.umlatron.view.SelectedPanel;
import edu.millersville.umlatron.view.umlLines.UMLLine;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;

/**
 *
 * @author John
 */
public class Generalization extends UMLLine implements SelectedPanel,java.io.Serializable {

    Rotate rotate = new Rotate();
    Polygon polygon = new Polygon();

    public Generalization(ClassBox a1, ClassBox a2) {
        super(a1, a2);
        createLine();
    }
    
    public Generalization(){}
    
    @Override
    public Generalization createLineFromLoad(){
        super.createLineFromLoad();
        createLine();
        return this;
    }
    
    
    
    private void createLine(){
        
        polygon.getPoints().addAll(new Double[]{
            0.0, 0.0,
            -12.0, -7.0,
            -12.0, 7.0

        });
        polygon.setFill(Color.WHITE);
        polygon.setStrokeWidth(1);
        polygon.setStroke(Color.BLACK);

     

        polygon.getTransforms().add(rotate);
        
        
        this.getChildren().add(polygon);
     
        updateHead();
        
     
        distance.removeListener(listener);
        distance.addListener(
                (ObservableValue<? extends Number> ov, Number old_state,
                        Number new_state) -> {

                    calculateAnchorPoints();
                    updateHead();


                });

    }
    
    @Override
    public void updateHead(){
        double slopeInDegrees = (Math.toDegrees(Math.atan2(line.getStartY() - line.getEndY(), line.getEndX() - line.getStartX())));
        rotate.setAngle(-slopeInDegrees);
        polygon.setTranslateX(line.endXProperty().get());
        polygon.setTranslateY(line.endYProperty().get());
    }
    
    
    
      

    
      
      /**
     * creates the currently selected panel for this Node
     * @param h the views HBox
     */
    @Override
    public void createAndGeneratePanel(HBox h) {

        h.getChildren().clear();
        DropShadow shadow = new DropShadow();

        Button setDashed = new Button();
        setDashed.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(setDashed, Priority.ALWAYS);
        setDashed.setOnAction((ActionEvent e) -> {
            setDashed();

        });
        setDashed.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        setDashed.setEffect(shadow);
                    }
                });
        setDashed.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        setDashed.setEffect(null);
                    }
                });

        Button setSolid = new Button();
        Image image = new Image("/images/solidgeneralization.png"); 
        setSolid.setGraphic(new ImageView(image));
        setSolid.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(setSolid, Priority.ALWAYS);
        setSolid.setOnAction((ActionEvent e) -> {
           setSolid();
        });
        setSolid.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        setSolid.setEffect(shadow);
                    }
                });
        setSolid.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        setSolid.setEffect(null);
                    }
                });
        
        Button deleteB = new Button("Delete");
        deleteB.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(deleteB, Priority.ALWAYS);
        deleteB.setOnAction((ActionEvent e) -> {
           destroy();
           h.getChildren().clear();
        });
        deleteB.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        deleteB.setEffect(shadow);
                    }
                });
        deleteB.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        deleteB.setEffect(null);
                    }
                });
        
        Label label = new Label("Currently selected node : Generalization ");


        h.getChildren().addAll(label,setDashed, setSolid,deleteB);

    }

}
