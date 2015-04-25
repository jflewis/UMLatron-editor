/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.millersville.umlatron.view.umlLines;

import edu.millersville.umlatron.view.AnchorPoint;
import edu.millersville.umlatron.view.ClassBox;
import edu.millersville.umlatron.view.SelectedPanel;
import edu.millersville.umlatron.view.umlLines.UMLLine;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
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

    public Generalization(AnchorPoint a1, AnchorPoint a2) {
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
        polygon.setStrokeWidth(3);
        polygon.setStroke(Color.NAVY);

     

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
        polygon.setStrokeWidth(3.0);
        polygon.setStroke(Color.NAVY);
    }
    
    
    
      

    
      
      /**
     * creates the currently selected panel for this Node
     * @param h the views HBox
     */
    @Override
    public void createAndGeneratePanel(HBox h) {

        h.getChildren().clear();
        DropShadow shadow = new DropShadow();
        
        Label label = new Label("Currently on:  ");
        label.setId("currentPanel");
        Image labelGeneralization = new Image("/images/Generalization.png", 35, 35, false, false);
        ImageView iv1 = new ImageView();
        iv1.setImage(labelGeneralization);
        label.setContentDisplay(ContentDisplay.BOTTOM);
        label.setGraphic(new ImageView(labelGeneralization));
        
        Button setDashed = new Button("Dashed  ");
        Image DashedGeneralization = new Image("/images/DashedGeneralization.png", 35, 35, false, false); 
        ImageView iv3 = new ImageView();
        iv3.setImage(DashedGeneralization);
        setDashed.setContentDisplay(ContentDisplay.RIGHT);
        setDashed.setGraphic(new ImageView(DashedGeneralization));
        setDashed.setMaxWidth(Double.MAX_VALUE);
        setDashed.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(setDashed, Priority.ALWAYS);
        setDashed.setOnAction((ActionEvent e) -> {
            setDashed();
            label.setContentDisplay(ContentDisplay.BOTTOM);
            label.setGraphic(new ImageView(DashedGeneralization));

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

        Button setSolid = new Button("Solid  ");
        Image Generalization = new Image("/images/Generalization.png", 35, 35, false, false);
        ImageView iv2 = new ImageView();
        iv2.setImage(Generalization);
        setSolid.setContentDisplay(ContentDisplay.RIGHT);
        setSolid.setGraphic(new ImageView(Generalization));
        setSolid.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(setSolid, Priority.ALWAYS);
        setSolid.setOnAction((ActionEvent e) -> {
           setSolid();
           label.setContentDisplay(ContentDisplay.BOTTOM);
           label.setGraphic(new ImageView(Generalization));
           
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
        
        Button deleteB = new Button("Delete  ");
        Image deleteClosed = new Image("/images/TrashCanClosed.png", 35, 35, false, false); 
        Image deleteOpen = new Image("/images/TrashCanOpen.png", 35, 35, false, false);
        ImageView iv4 = new ImageView();
        iv4.setImage(deleteClosed);
        deleteB.setContentDisplay(ContentDisplay.RIGHT);
        deleteB.setGraphic(new ImageView(deleteClosed));
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
                                            deleteB.setGraphic(new ImageView(deleteOpen));
                                    }
                            });
            deleteB.addEventHandler(MouseEvent.MOUSE_EXITED,
                            new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent e) {
                                            deleteB.setEffect(null);
                                            deleteB.setGraphic(new ImageView(deleteClosed));
                                    }
                            });

        setSolid.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                    	setSolid.setId("selectedButton");
                        setDashed.setId("");
                    }
                });
        setDashed.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                    	setDashed.setId("selectedButton");
                    	setSolid.setId("");	                        
                    }
                });
       


        h.getChildren().addAll(label,setDashed, setSolid,deleteB);

    }

}
