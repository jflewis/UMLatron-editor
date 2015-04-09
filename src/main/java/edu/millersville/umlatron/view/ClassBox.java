package edu.millersville.umlatron.view;
import javafx.scene.control.ScrollPane;

import java.util.ArrayList;
import java.util.Observable;

import edu.millersville.umlatron.model.LineType;
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
 * @author Greg Polhemus
 */
public class ClassBox extends VBox implements AnchorPoint, SelectedPanel {

    private double initX;
    private double initY;
    private double height, width;
    private int anchorCount;
    private Point2D[] anchorPoints;
    private ArrayList<LineType> pointTypes;
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
    private Text textHolder = new Text();
    private double oldHeight = 0;

    public ClassBox(double x, double y) {

        super();
        height = 178.0;
        width = 167.0;
        anchorCount = 4;
        anchorPoints = new Point2D[anchorCount];
        setAnchorPoints(x, y);
        pointTypes = new ArrayList<LineType>();
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
        
     
        
        textHolder.textProperty().bind(classTextName.textProperty());
        textHolder.boundsInLocalProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
            	textHolder.setWrappingWidth(classTextName.getWidth() - 25);
                if (oldHeight != newValue.getHeight() ) {
                    System.out.println("newValue = " + newValue.getHeight());
                    oldHeight = newValue.getHeight();
                    classTextName.setPrefHeight(textHolder.getLayoutBounds().getHeight() + 20); // +20 is for paddings
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

        classFunctions = new TextArea();
        classFunctions.setPromptText(functions);
        classFunctions.setPrefRowCount(3);
        classFunctions.setPrefColumnCount(10);
        classFunctions.setWrapText(true);
        classFunctions.setMouseTransparent(true);
        classFunctions.setEditable(false);
        classFunctions.isResizable();
        ClassBox.setVgrow(classFunctions, Priority.ALWAYS); 
        
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
                updateXAnchorPoints(newXPosition);
            } else if (newXPosition >= this.sceneProperty().get().getX()) {
                setTranslateX(this.sceneProperty().get().getWidth()
                        - widthProperty().getValue());
                updateXAnchorPoints(this.sceneProperty().get().getWidth()
                        - widthProperty().getValue());
            } else {
                setTranslateX(0);
                updateXAnchorPoints(0);
            }
            for (int i = 0; i < lines.size(); ++i) {
                lines.get(i).updateAnchorPoints();
                if (pointTypes.get(i).equals(LineType.START)) {
                    lines.get(i).setStartX(
                            anchorPoints[lines.get(i).getAnchorPoint1Int()]
                            .getX());
                }
                if (pointTypes.get(i).equals(LineType.END)) {
                    lines.get(i).setEndX(
                            anchorPoints[lines.get(i).getAnchorPoint2Int()]
                            .getX());
                }
            }
            if ((newYPosition >= this.sceneProperty().get().getY())
                    && (newYPosition <= this.sceneProperty().get().getHeight()
                    - (this.sceneProperty().get().getY() + heightProperty()
                    .getValue()))) {
                setTranslateY(newYPosition);
                updateYAnchorPoints(newYPosition);

            } else if (newYPosition >= this.sceneProperty().get().getY()) {
                setTranslateY(this.sceneProperty().get().getHeight()
                        - heightProperty().getValue());
                updateYAnchorPoints(this.sceneProperty().get().getHeight()
                        - heightProperty().getValue());
            } else {
                setTranslateY(0);
                updateYAnchorPoints(0);
            }
            for (int i = 0; i < lines.size(); ++i) {
                lines.get(i).updateAnchorPoints();
                if (pointTypes.get(i).equals(LineType.START)) {
                    lines.get(i).setStartY(
                            anchorPoints[lines.get(i).getAnchorPoint1Int()]
                            .getY());
                }
                if (pointTypes.get(i).equals(LineType.END)) {
                    lines.get(i).setEndY(
                            anchorPoints[lines.get(i).getAnchorPoint2Int()]
                            .getY());
                }
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
            deleteSelf();
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

    private void deleteSelf() {
        Pane pane = (Pane) this.getParent();
        int t = lines.size();
        for (int i = t; i > 0; --i) {
            lines.get(i-1).deleteSelf();
        }
        pane.getChildren().remove(this);
    }

    /**
     * ********************************************************************************************************
     */
    // Anchor Points Set/Updates
    public void setAnchorPoints(double x, double y) {
        anchorPoints[0] = new Point2D(x + (width / 2), y); // top
        anchorPoints[1] = new Point2D(x, y + (height / 2)); // left
        anchorPoints[2] = new Point2D(x + width, y + (height / 2)); // right
        anchorPoints[3] = new Point2D(x + (width / 2), y + height); // bottom
    }

    public void updateXAnchorPoints(double x) {
        anchorPoints[0] = new Point2D(x + (width / 2), anchorPoints[0].getY());
        anchorPoints[1] = new Point2D(x, anchorPoints[1].getY());
        anchorPoints[2] = new Point2D(x + width, anchorPoints[2].getY());
        anchorPoints[3] = new Point2D(x + (width / 2), anchorPoints[3].getY());
    }

    public void updateYAnchorPoints(double y) {
        anchorPoints[0] = new Point2D(anchorPoints[0].getX(), y);
        anchorPoints[1] = new Point2D(anchorPoints[1].getX(), y + (height / 2));
        anchorPoints[2] = new Point2D(anchorPoints[2].getX(), y + (height / 2));
        anchorPoints[3] = new Point2D(anchorPoints[3].getX(), y + height);
    }

    public Point2D getAnchorPoint(int i) {
        if (i < anchorPoints.length) {
            return anchorPoints[i];
        } else {
            return null;
        }
    }

    @Override
    public int getAnchorCount() {
        return anchorCount;
    }

    @Override
    public void addLineType(LineType str) {
        pointTypes.add(str);
        
    }

    @Override
    public void addLine(UMLLine line) {
        lines.add(line);
    }

    @Override
    public void deleteLine(int id) {
        Pane pane = (Pane) this.getParent();
        int t = lines.size();
        for (int i = 0; i < lines.size(); ++i) {
            if (lines.get(i).getIntId() == id) {
                if (pane != null) {
                    if(pointTypes.get(i).equals(LineType.END)){
                        pane.getChildren().remove(lines.get(i));
                    }
                    pointTypes.remove(pointTypes.get(i));
                    lines.remove(lines.get(i));
                    break;
                    
                }
                
            }
            

        }
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
    
    public void revertActions(TextArea text){
    	if(text == classTextName){
    		classMethods.setEditable(false);
    		classMethods.setMouseTransparent(true);
    		classMethods.setStyle("-fx-background-color: white");
    		classFunctions.setEditable(false);
    		classFunctions.setMouseTransparent(true);
    		classFunctions.setStyle("-fx-background-color: white");
    	} else if(text == classMethods){
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
            deleteSelf();
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
