/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.millersville.umlatron.view;

import edu.millersville.umlatron.Util.AnchorInfo;
import edu.millersville.umlatron.view.umlLines.UMLLine;
import edu.millersville.umlatron.view.umlRecursiveLines.UMLRecursiveLine;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import javafx.beans.binding.DoubleBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 *
 * @author John
 */
public class User extends VBox implements AnchorPoint, SelectedPanel, java.io.Externalizable {

    private double initX;
    private double initY;
    private Point2D dragAnchor;
    private ArrayList<UMLLine> lines = new ArrayList<>();
    private ArrayList<UMLRecursiveLine> recursiveLines = new ArrayList<>();
    TextField textField;
    ImageView stickFigure;

    public User() {
        this(0, 0);
    }

    public User(double x, double y) {
        super();
        textField = new TextField();
        textField.setPromptText("User");
        textField.setEditable(false);
        textField.setMaxWidth(55);
        textField.setStyle("-fx-border-color:transparent;-fx-background-color:transparent");
        stickFigure = new ImageView(new Image("/images/stickFigure.png"));
        //this.setHeight(178.0);
        //this.setWidth(167.0);
        setCursor(Cursor.OPEN_HAND);
        setTranslateX(x);
        setTranslateY(y);
        this.getChildren().addAll(stickFigure, textField);

        /*
         * Movement Handling/Mouse Events Creation of ClassBox on Mouse Press
         * Mouse Drag determined by getting the current value of the classBox
         * after creation (it is initialized to 0.0 by default)
         */
        setOnMousePressed((event) -> {
            // when mouse is pressed, store initial position
            initX = getTranslateX();
            initY = getTranslateY();
            dragAnchor = new Point2D(event.getSceneX(), event.getSceneY());
            event.consume();
        });
        setOnMouseDragged((event) -> {
            double dragX = event.getSceneX() - dragAnchor.getX();
            double dragY = event.getSceneY() - dragAnchor.getY();
            double newXPosition = initX + dragX;
            double newYPosition = initY + dragY;
          //  width = widthProperty().getValue();
            //  height = heightProperty().getValue();

            if ((newXPosition >= this.sceneProperty().get().getX())
                    && (newXPosition <= this.sceneProperty().get().getWidth()
                    - (this.sceneProperty().get().getX() + widthProperty()
                    .getValue()))) {

                setTranslateX(newXPosition);
            } else if (newXPosition >= this.sceneProperty().get().getX()) {
                setTranslateX(this.sceneProperty().get().getWidth()
                        - widthProperty().getValue());
            } else {
                setTranslateX(0);
            }

            if ((newYPosition >= this.sceneProperty().get().getY())
                    && (newYPosition <= this.sceneProperty().get().getHeight()
                    - (this.sceneProperty().get().getY() + heightProperty()
                    .getValue()))) {

                setTranslateY(newYPosition);

            } else if (newYPosition >= this.sceneProperty().get().getY()) {
                setTranslateY(this.sceneProperty().get().getHeight()
                        - heightProperty().getValue());

            } else {
                setTranslateY(0);
            }

            event.consume();
        });

        setOnMouseClicked(event -> {
            event.consume();
        });

    }

    @Override
    public AnchorInfo getNorthPoint() {

        DoubleBinding x = this.translateXProperty().add(this.widthProperty().divide(2));
        DoubleBinding y = this.translateYProperty().add(0);

        AnchorInfo northPoint = new AnchorInfo(x, y);
        return northPoint;
    }

    @Override
    public AnchorInfo getSouthPoint() {
        DoubleBinding x = this.translateXProperty().add(this.widthProperty().divide(2));
        DoubleBinding y = this.translateYProperty().add(this.getHeight());

        AnchorInfo southPoint = new AnchorInfo(x, y);
        return southPoint;

    }

    @Override
    public AnchorInfo getEastPoint() {
        DoubleBinding x = this.translateXProperty().add(this.getWidth());
        DoubleBinding y = this.translateYProperty().add(this.getHeight() / 2);

        AnchorInfo eastPoint = new AnchorInfo(x, y);
        return eastPoint;

    }

    @Override
    public AnchorInfo getWestPoint() {
        DoubleBinding x = this.translateXProperty().add(0);
        DoubleBinding y = this.translateYProperty().add(this.getHeight() / 2);

        AnchorInfo westPoint = new AnchorInfo(x, y);
        return westPoint;
    }

    @Override
    public void addLine(UMLLine line) {
        lines.add(line);
    }

    public void addRecursiveLine(UMLRecursiveLine line) {
        recursiveLines.add(line);
    }

    @Override
    public void removeLine(UMLLine line) {
        lines.remove(line);
    }

    public void updateAnchorPoints() {
        for (UMLLine line : lines) {
            line.resetAnchorPoints();
            line.calculateAnchorPoints();
        }
    }

    @Override
    public void removeRecursiveLine(UMLRecursiveLine line) {
        recursiveLines.remove(line);
    }

    @Override
    public double getWidthAnchorPoint() {
        return this.getWidth();
    }

    @Override
    public double getHeightAnchorPoint() {
        return this.getHeight();
    }

    ////////////////////////

    public void applyActions(TextField text) {
        text.requestFocus();
        text.setEditable(true);
        text.setMouseTransparent(false);
    }

    public void removeActions() {
        textField.setEditable(false);
        textField.setMouseTransparent(true);
        textField.setEditable(false);
        textField.setMouseTransparent(true);

    }
    
    public void destroy() {
        Pane pane = (Pane) this.getParent();
        int size = lines.size() - 1;
        for (int i = size; i >= 0; i--) {
            lines.get(i).destroy();
        }
        lines.clear();
        size = recursiveLines.size() - 1;
        for (int i = size; i >= 0; i--) {
            recursiveLines.get(i).destroy();
        }
        recursiveLines.clear();
        pane.getChildren().remove(this);
    }



//////////////////////////////////////////////////////////////
/**
 * creates the currently selected panel for this Node
 *
 * @param h the views HBox
 */
@Override
        public void createAndGeneratePanel(HBox h) {

        h.getChildren().clear();
        DropShadow shadow = new DropShadow();

        Button editName = new Button("Edit user id");
        editName.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(editName, Priority.ALWAYS);
        editName.setOnAction((ActionEvent e) -> {
        	applyActions(textField);          
        });
        editName.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
        public void handle(MouseEvent e) {
                        editName.setEffect(shadow);
                    }
                });
        editName.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
        public void handle(MouseEvent e) {
                        editName.setEffect(null);
                    }
                });


        Button deleteB = new Button("Delete");
        deleteB.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(deleteB, Priority.ALWAYS);
        deleteB.setOnAction((ActionEvent e) -> {
        	removeActions();
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

        Label label = new Label("Currently selected node : User ");
        h.getChildren().addAll(editName, deleteB);
    }
    
    
    
    
    
    ////////////////////////////////////////////////////////////
    @Override
        public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(this.getTranslateX());
        out.writeDouble(this.getTranslateY());
        out.writeUTF(textField.getText());
       // out.writeDouble(this.getHeight());
        out.writeDouble(this.getWidth());
        
    }

    @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setTranslateX(in.readDouble());
        this.setTranslateY(in.readDouble());
        textField.setText( in.readUTF());
        //this.setHeight(in.readDouble());
        //this.setWidth(in.readDouble());

    }
	
        
    
}
