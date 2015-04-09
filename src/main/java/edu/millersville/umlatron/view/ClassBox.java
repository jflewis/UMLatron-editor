package edu.millersville.umlatron.view;

import javafx.scene.control.ScrollPane;
import edu.millersville.umlatron.Util.AnchorInfo;
import java.util.ArrayList;
import javafx.beans.binding.DoubleBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.geometry.HPos;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollBar;


/**
 *
 * @authors Greg Polhemus , John L., Matt H.
 */
public class ClassBox extends VBox implements AnchorPoint, SelectedPanel {

    private double initX;
    private double initY;
    double height, width;
    private int anchorCount;
    private Point2D[] anchorPoints;
    private ArrayList<UMLLine> lines;
    private Point2D dragAnchor;
    private String name = "Enter A Class Name Here";
    private String methods = "Enter Methods Here";
    private String functions = "Enter Functions Here";
    private TextArea classTextName;
    private TextArea classMethods;
    private TextArea classFunctions;
    private DropShadow borderGlow;
    private DropShadow noGlow;
    private Text nameHolder = new Text();
    private Text functionsHolder = new Text();
    private Text methodsHolder = new Text();
    private double textNameHeight = 0;
    private double methodsHeight = 0;
    private double functionsHeight = 0;


    public ClassBox(double x, double y) {

        super();
        height = 178.0;
        width = 167.0;
        anchorCount = 4;
        anchorPoints = new Point2D[anchorCount];
        lines = new ArrayList<UMLLine>();
        setCursor(Cursor.OPEN_HAND);
        setTranslateX(x);
        setTranslateY(y);
        isResizable();
        System.out.println(computePrefHeight(height));
        setStyle("-fx-border-style: solid;" + "-fx-border-width: 2;"
                + "-fx-border-color: black;");
        
        //highlight for currently selected classbox
        borderGlow = new DropShadow();
    	borderGlow.setColor(Color.GREEN);
    	borderGlow.setOffsetX(0f);
    	borderGlow.setOffsetY(0f);
    	borderGlow.setWidth(70);
    	borderGlow.setHeight(70);
    	//remove highlighting
    	noGlow = new DropShadow();
    	noGlow.setColor(Color.GREEN);
    	noGlow.setOffsetX(0f);
    	noGlow.setOffsetY(0f);
    	noGlow.setWidth(0);
    	noGlow.setHeight(0);

        /*
         * Children of ClassBox Consists of 3 TextAreas with default column and
         * row sizes VBox grows to meet these upon creation They are set to wrap
         * text, given a prompt text, and are set to transparent until
         * corresponding button says otherwise
         */    
    	
        
        classTextName = new TextArea();
        classTextName.setPromptText(name);
        classTextName.setPrefRowCount(1);
        classTextName.setPrefColumnCount(10);
        classTextName.setWrapText(true);
        classTextName.setMouseTransparent(true);
        classTextName.setEditable(false);
        classTextName.isResizable();
        ClassBox.setVgrow(classTextName, Priority.ALWAYS); 
        
        nameHolder.textProperty().bind(classTextName.textProperty());
        nameHolder.boundsInLocalProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
            	nameHolder.setWrappingWidth(classTextName.getWidth() - 25);
                if (textNameHeight != newValue.getHeight() ) {
                    System.out.println("newValue = " + newValue.getHeight());
                    textNameHeight = newValue.getHeight();
                    classTextName.setPrefHeight(nameHolder.getLayoutBounds().getHeight() + 20); // +20 is for paddings
                }
                
            }
        });
 
        classMethods = new TextArea();
        classMethods.setPromptText(methods);
        classMethods.setPrefRowCount(2);
        classMethods.setPrefColumnCount(10);
        classMethods.setWrapText(true);
        classMethods.setMouseTransparent(true);
        classMethods.setEditable(false);
        classMethods.isResizable();
        ClassBox.setVgrow(classMethods, Priority.ALWAYS); 
        
        methodsHolder.textProperty().bind(classMethods.textProperty());
        methodsHolder.boundsInLocalProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
            	methodsHolder.setWrappingWidth(classMethods.getWidth() - 25);
                if (methodsHeight != newValue.getHeight() ) {
                    System.out.println("newValue = " + newValue.getHeight());
                    methodsHeight = newValue.getHeight();
                    classMethods.setPrefHeight(methodsHolder.getLayoutBounds().getHeight() + 20); // +20 is for paddings
                }
                
            }
        });

        classFunctions = new TextArea();
        classFunctions.setPromptText(functions);
        classFunctions.setPrefRowCount(3);
        classFunctions.setPrefColumnCount(10);
        classFunctions.setWrapText(true);
        classFunctions.setMouseTransparent(true);
        classFunctions.setEditable(false);
        classFunctions.isResizable();
        ClassBox.setVgrow(classFunctions, Priority.ALWAYS); 
        
        functionsHolder.textProperty().bind(classFunctions.textProperty());
        functionsHolder.boundsInLocalProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
            	functionsHolder.setWrappingWidth(classFunctions.getWidth() - 25);
                if (functionsHeight != newValue.getHeight() ) {
                    System.out.println("newValue = " + newValue.getHeight());
                    functionsHeight = newValue.getHeight();
                    classFunctions.setPrefHeight(functionsHolder.getLayoutBounds().getHeight() + 20); // +20 is for paddings
                }
                
            }
        });
        
        getChildren().addAll(classTextName, classMethods, classFunctions);

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

        // Dragging Movement of ClassBox
        // *********************************************************************************/
        setOnMouseDragged((event) -> {
            double dragX = event.getSceneX() - dragAnchor.getX();
            double dragY = event.getSceneY() - dragAnchor.getY();
            double newXPosition = initX + dragX;
            double newYPosition = initY + dragY;
            width = widthProperty().getValue();
            height = heightProperty().getValue();

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

        /**
         * ******************************************************************************************************
         */

        /*
         * Menu Functionality For Class Box with right click Currently set to
         * just Delete Added into the menu that the textArea gives, for each
         * textArea Cannot Delete Unless TextArea has focus, currently
         */
        MenuItem delete = new MenuItem("delete");
        delete.setOnAction(event -> {
            // deleteSelf();
        });

        ContextMenu contextMenu = new ContextMenu(delete);

        // Right Click Menu Event with addition of Delete in each TextArea
        // ****************************************/
        classTextName.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                System.out.println("right click registered");
                contextMenu.show(this, event.getScreenX(),
                        event.getScreenY() - 35);
            } else {
                contextMenu.hide();
            }
            event.consume();
        });

        classFunctions.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                // System.out.println("hello");
                contextMenu.show(this, event.getScreenX(),
                        event.getScreenY() - 35);
            } else {
                contextMenu.hide();
            }
            event.consume();
        });

        classMethods.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                // System.out.println("hello");
                contextMenu.show(this, event.getScreenX(),
                        event.getScreenY() - 35);
            } else {
                contextMenu.hide();
            }
            event.consume();
        });

        setOnMouseClicked(event -> {
            event.consume();
        });

    }

    // ********************************************************************************************************
    @Override
    public AnchorInfo getNorthPoint() {

        DoubleBinding x = this.translateXProperty().add(this.width / 2);
        DoubleBinding y = this.translateYProperty().add(0);

        AnchorInfo northPoint = new AnchorInfo(x, y);
        return northPoint;
    }

    @Override
    public AnchorInfo getSouthPoint() {
        DoubleBinding x = this.translateXProperty().add(this.width / 2);
        DoubleBinding y = this.translateYProperty().add(this.height);

        AnchorInfo southPoint = new AnchorInfo(x, y);
        return southPoint;

    }

    @Override
    public AnchorInfo getEastPoint() {
        DoubleBinding x = this.translateXProperty().add(this.width);
        DoubleBinding y = this.translateYProperty().add(this.height / 2);

        AnchorInfo eastPoint = new AnchorInfo(x, y);
        return eastPoint;

    }

    @Override
    public AnchorInfo getWestPoint() {
        DoubleBinding x = this.translateXProperty().add(0);
        DoubleBinding y = this.translateYProperty().add(this.height / 2);

        AnchorInfo westPoint = new AnchorInfo(x, y);
        return westPoint;
    }

    @Override
    public void addLine(UMLLine line) {
        lines.add(line);
    }

    @Override
    public void removeLine(UMLLine line) {
        lines.remove(line);

    }


    /**
     * *********************************************************************************************
     * TextArea functionality
     */
    public void applyActions(TextArea text) {
    	text.requestFocus();
    	text.setEditable(true);
    	text.setMouseTransparent(false);
    	text.setStyle("-fx-background-color: green");
    	text.getParent().setEffect(borderGlow);
    }
    	
  //*********************************************************************************************
 
    public void revertActions(TextArea text) {
        if (text == classTextName) {
            classMethods.setEditable(false);
            classMethods.setMouseTransparent(true);
            classMethods.setStyle("-fx-background-color: white");
            classFunctions.setEditable(false);
            classFunctions.setMouseTransparent(true);
            classFunctions.setStyle("-fx-background-color: white");
        } else if (text == classMethods) {
            classTextName.setEditable(false);
            classTextName.setMouseTransparent(true);
            classTextName.setStyle("-fx-background-color: white");
            classFunctions.setEditable(false);
            classFunctions.setMouseTransparent(true);
            classFunctions.setStyle("-fx-background-color: white");
        } else {
            classTextName.setEditable(false);
            classTextName.setMouseTransparent(true);
            classTextName.setStyle("-fx-background-color: white");
            classMethods.setEditable(false);
            classMethods.setMouseTransparent(true);
            classMethods.setStyle("-fx-background-color: white");
        }
    }

    public void removeActions(){
    	revertActions(classTextName);
    	revertActions(classMethods);
    	revertActions(classFunctions);
    	classTextName.getParent().setEffect(noGlow);
    	
    }
    /*
     * Set up for textArea editing
     * 
    public void createTextAreaMenu(VBox vbox){
    	VBox menuOptions = new VBox(); 	
    
    	menuOptions.getChildren().clear();
    	Button bold = new Button("Bold");
    	bold.setMaxWidth(Double.MAX_VALUE);
    	VBox.setVgrow(bold, Priority.ALWAYS);
    	bold.setOnAction((ActionEvent e) -> {
    		classTextName.getSelectedText();
    		classTextName.setStyle("-fx-font-weight:bold");
    	});
    	menuOptions.getChildren().addAll(bold);
    	
    	
    	
    }
    */

    public void destroy() {
        Pane pane = (Pane) this.getParent();
        int size = lines.size() - 1;
        for (int i = size; i >= 0; i--) {
            lines.get(i).destroy();
        }
        lines.clear();
        pane.getChildren().remove(this);
    }

    /**
     * creates the currently selected panel for this Node
     *
     * @param h the views HBox
     */
    @Override
    public void createAndGeneratePanel(HBox h) {

        h.getChildren().clear();
        DropShadow shadow = new DropShadow();

        Button editName = new Button("Edit Name");
        editName.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(editName, Priority.ALWAYS);
        editName.setOnAction((ActionEvent e) -> {
        	TextArea text = classTextName;
        	applyActions(text);
        	revertActions(text);

          
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

        Button editAttr = new Button("Edit attributes");
        editAttr.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(editAttr, Priority.ALWAYS);
        editAttr.setOnAction((ActionEvent e) -> {
            TextArea text = classMethods;
            applyActions(text);
            revertActions(text);
        });
        editAttr.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        editAttr.setEffect(shadow);
                    }
                });
        editAttr.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        editAttr.setEffect(null);
                    }
                });

        Button editOps = new Button("Edit operations");
        editOps.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(editOps, Priority.ALWAYS);
        editOps.setOnAction((ActionEvent e) -> {
            TextArea text = classFunctions;
            applyActions(text);
            revertActions(text);
        });
        editOps.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        editOps.setEffect(shadow);
                    }
                });
        editOps.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        editOps.setEffect(null);
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

        Label label = new Label("Currently selected node : Class Box ");
        h.getChildren().addAll(label, editName, editAttr, editOps, deleteB);
    }

}
