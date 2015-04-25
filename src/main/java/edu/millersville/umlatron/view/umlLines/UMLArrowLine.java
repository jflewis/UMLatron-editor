package edu.millersville.umlatron.view.umlLines;

import edu.millersville.umlatron.view.AnchorPoint;
import edu.millersville.umlatron.view.ClassBox;
import edu.millersville.umlatron.view.SelectedPanel;
import edu.millersville.umlatron.view.umlLines.UMLLine;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.transform.Rotate;

/**
 *
 * @author Matthew Hipszer
 *
 */
public class UMLArrowLine extends UMLLine implements SelectedPanel {

    final private Line minSpreadLine = new Line(0,
                0, -10,
                -10);
    final private Line maxSpreadLine = new Line(0,
                0, -10,
                10);
    final Group group = new Group();
    Rotate rotate = new Rotate();

    /**
	 * A default constructor used for loading
	 */
    public UMLArrowLine(){}
    
    /**
     *
     * @param a1 The AnchorPoint that the starting point of the line is attached
     * to.
     * @param a2 The AnchorPoint that the ending point of the line is attached
     * to.
     */
    public UMLArrowLine(AnchorPoint a1, AnchorPoint a2) {
        super(a1, a2);
        createArrow();
                    
    }
    
    @Override
    public UMLArrowLine createLineFromLoad(){
        super.createLineFromLoad();
        createArrow();
        return this;
        
    }
    
    /**
     * Creates the Arrow at the end of the line.
     */
    private void createArrow(){
   
        group.getChildren().addAll(minSpreadLine,maxSpreadLine);
        minSpreadLine.setStrokeWidth(3);
        maxSpreadLine.setStrokeWidth(3);
        minSpreadLine.setStroke(Color.NAVY);
        maxSpreadLine.setStroke(Color.NAVY);
        group.getTransforms().add(rotate);
        this.getChildren().add(group); 
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
        group.setTranslateX(line.endXProperty().get());
        group.setTranslateY(line.endYProperty().get());
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
        Image PlainClassBox = new Image("/images/SolidLine.png", 35, 35, false, false);
        ImageView iv1 = new ImageView();
        iv1.setImage(PlainClassBox);
        label.setContentDisplay(ContentDisplay.BOTTOM);
        label.setGraphic(new ImageView(PlainClassBox));
        HBox.setHgrow(label, Priority.ALWAYS);
        label.setMaxWidth(Double.MAX_VALUE);

        Button setDashed = new Button("Dashed  ");
        Image DashedLine = new Image("/images/DashedLine.png", 35, 35, false, false);
        ImageView iv2 = new ImageView();
        iv2.setImage(DashedLine);
        setDashed.setContentDisplay(ContentDisplay.RIGHT);
        setDashed.setGraphic(new ImageView(DashedLine));
        setDashed.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(setDashed, Priority.ALWAYS);
        setDashed.setOnAction((ActionEvent e) -> {
            setDashed();
            label.setContentDisplay(ContentDisplay.BOTTOM);
            label.setGraphic(new ImageView(DashedLine));


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
        Image SolidLine = new Image("/images/SolidLine.png", 35, 35, false, false);
        ImageView iv3 = new ImageView();
        iv3.setImage(SolidLine);
        setSolid.setContentDisplay(ContentDisplay.RIGHT);
        setSolid.setGraphic(new ImageView(SolidLine));
        setSolid.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(setSolid, Priority.ALWAYS);
        setSolid.setOnAction((ActionEvent e) -> {
            setSolid();
            label.setContentDisplay(ContentDisplay.BOTTOM);
            label.setGraphic(new ImageView(SolidLine));
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
        Image deleteImg = new Image("/images/TrashCanOpen.png", 35, 35, false, false); 
        ImageView iv5 = new ImageView();
        iv5.setImage(deleteImg);
        deleteB.setContentDisplay(ContentDisplay.RIGHT);
        deleteB.setGraphic(new ImageView(deleteImg));
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
        
       


        h.getChildren().addAll(label,setDashed, setSolid,deleteB);

    }
    
    /*
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(startNode);
        out.writeObject(endNode);
        
    }

 

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        startNode  = (ClassBox)in.readObject();
        endNode = (ClassBox)in.readObject();

    }
    */
    
}
