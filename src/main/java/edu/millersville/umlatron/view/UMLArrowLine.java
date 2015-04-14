package edu.millersville.umlatron.view;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
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


    public UMLArrowLine(){}
    /**
     *
     * @param a1 The AnchorPoint that the starting point of the line is attached
     * to.
     * @param a2 The AnchorPoint that the ending point of the line is attached
     * to.
     */
    public UMLArrowLine(ClassBox a1, ClassBox a2) {
        super(a1, a2);
        createLine();
                    
    }
    
    @Override
    public UMLArrowLine createLineFromLoad(){
        super.createLineFromLoad();
        createLine();
        return this;
        
    }
    
    private void createLine(){
   
        group.getChildren().addAll(minSpreadLine,maxSpreadLine);
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

        Button setDashed = new Button("Dashed");
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

        Button setSolid = new Button("Solid");
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
        
        Label label = new Label("Currently selected node : Line ");


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
