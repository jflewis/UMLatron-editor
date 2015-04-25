package edu.millersville.umlatron.view;

import edu.millersville.umlatron.view.umlRecursiveLines.UMLRecursiveLine;
import edu.millersville.umlatron.view.umlLines.UMLLine;
import edu.millersville.umlatron.Util.AnchorInfo;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;

import javafx.beans.binding.DoubleBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;



/**
 *
 * @authors Greg Polhemus , John L., Matt H.
 */
public class ClassBox extends VBox implements AnchorPoint, SelectedPanel,java.io.Externalizable {

    private double initX;
    private double initY;
   // double height = 178.0;
   // double width = 167.0;
    private int anchorCount;
    private Point2D[] anchorPoints;
    private ArrayList<UMLLine> lines = new ArrayList<>();
    private ArrayList<UMLRecursiveLine> recursiveLines = new ArrayList<>();
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
    
    public ClassBox(){this(0,0);}


    public ClassBox(double x, double y) {

        super(); 
        this.setHeight(178.0);
        this.setWidth(167.0);
        setCursor(Cursor.OPEN_HAND);
        setTranslateX(x);
        setTranslateY(y);
        isResizable();
        setSpacing(5);
        
        //highlight for currently selected classbox
        borderGlow = new DropShadow();
    	borderGlow.setColor(Color.NAVY);
    	borderGlow.setOffsetX(0f);
    	borderGlow.setOffsetY(0f);
    	borderGlow.setWidth(30);
    	borderGlow.setHeight(30);
    	//remove highlighting
    	noGlow = new DropShadow();
    	noGlow.setColor(Color.NAVY);
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
        classTextName.setPrefRowCount(2);
        classTextName.setPrefColumnCount(11);
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
                    textNameHeight = newValue.getHeight();
                    classTextName.setPrefHeight(nameHolder.getLayoutBounds().getHeight() + 20); // +20 is for paddings
                   //setHeight(heightProperty().getValue());
                    updateAnchorPoints();
                    
                }
                
                
            }
           
        });
 
        classMethods = new TextArea();
        classMethods.setPromptText(methods);
        classMethods.setPrefRowCount(2);
        classMethods.setPrefColumnCount(11);
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
                    methodsHeight = newValue.getHeight();
                    classMethods.setPrefHeight(methodsHolder.getLayoutBounds().getHeight() + 20); // +20 is for paddings
                   // width = widthProperty().getValue();
                    //height = heightProperty().getValue();
                  //  setHeight(heightProperty().getValue());
                    updateAnchorPoints();
                }  
            }
        });

        classFunctions = new TextArea();
        classFunctions.setPromptText(functions);
        classFunctions.setPrefRowCount(2);
        classFunctions.setPrefColumnCount(11);
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
                    functionsHeight = newValue.getHeight();
                    classFunctions.setPrefHeight(functionsHolder.getLayoutBounds().getHeight() + 20); // +20 is for paddings
                 //   width = widthProperty().getValue();
                //    setHeight(heightProperty().getValue());
                    updateAnchorPoints();
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
            destroy();
        });

        ContextMenu contextMenu = new ContextMenu(delete);

        // Right Click Menu Event with addition of Delete in each TextArea
        // ****************************************/
        classTextName.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(this, event.getScreenX(),
                        event.getScreenY() - 35);
            } else {
                contextMenu.hide();
            }
            event.consume();
        });

        classFunctions.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(this, event.getScreenX(),
                        event.getScreenY() - 35);
            } else {
                contextMenu.hide();
            }
            event.consume();
        });

        classMethods.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
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

        DoubleBinding x = this.translateXProperty().add(this.widthProperty().divide(2) );
        DoubleBinding y = this.translateYProperty().add(0);

        AnchorInfo northPoint = new AnchorInfo(x, y);
        return northPoint;
    }

    @Override
    public AnchorInfo getSouthPoint() {
    	DoubleBinding x = this.translateXProperty().add(this.widthProperty().divide(2) );
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
    
    @Override
    public void addRecursiveLine(UMLRecursiveLine line) {
    	recursiveLines.add(line);
    }
    
    @Override
    public void removeLine(UMLLine line) {
        lines.remove(line);
    }
    
    public void updateAnchorPoints() {
    	for(UMLLine line: lines){
    		line.resetAnchorPoints();
    		line.calculateAnchorPoints();
    	}
    	
    }

    @Override
    public void removeRecursiveLine(UMLRecursiveLine line) {
    	recursiveLines.remove(line);
	}

    @Override
    public double getWidthAnchorPoint(){
        return this.getWidth();
    }
    
    @Override
    public double getHeightAnchorPoint(){
        return this.getHeight();
    }


    /**
     * *********************************************************************************************
     * TextArea functionality
     */
    public void applyActions(TextArea text) {
    	text.requestFocus();
    	text.setEditable(true);
    	text.setMouseTransparent(false);
    	text.setEffect(borderGlow);
    	text.getParent().setEffect(borderGlow);
    }
    public void applySelection(){
    	this.setEffect(borderGlow);
    	this.setId("");
    	
    }
    public void removeSelection(){
    	this.setEffect(noGlow);
    	this.setId("");
    }
    	
  //*********************************************************************************************
 
    public void revertActions(TextArea text) {
        if (text == classTextName) {
            classMethods.setEditable(false);
            classMethods.setMouseTransparent(true);
            classMethods.setEffect(noGlow);
            classFunctions.setEditable(false);
            classFunctions.setMouseTransparent(true);
            classFunctions.setEffect(noGlow);
        } else if (text == classMethods) {
            classTextName.setEditable(false);
            classTextName.setMouseTransparent(true);
            classTextName.setEffect(noGlow);
            classFunctions.setEditable(false);
            classFunctions.setMouseTransparent(true);
            classFunctions.setEffect(noGlow);
        } else {
            classTextName.setEditable(false);
            classTextName.setMouseTransparent(true);
            classTextName.setEffect(noGlow);
            classMethods.setEditable(false);
            classMethods.setMouseTransparent(true);
            classMethods.setEffect(noGlow);
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
        size = recursiveLines.size() - 1;
        for (int i = size; i >= 0; i--) {
            recursiveLines.get(i).destroy();
        }
        recursiveLines.clear();
        pane.getChildren().remove(this);
    }

    public void defaultLabel(Label label) {
    	label.setId("currentPanel");
    	Image TopClassBox = new Image("/images/ClassBox.png", 35, 35, false, false);
        ImageView iv2 = new ImageView();
        iv2.setImage(TopClassBox);
        label.setContentDisplay(ContentDisplay.RIGHT);
        label.setGraphic(new ImageView(TopClassBox));
    	
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
        
        h.setPadding(new Insets(8, 0, 0, 0));
        Label label = new Label("Currently On: ");
        label.setId("currentPanel");
        Image PlainClassBox = new Image("/images/ClassBox.png", 35, 35, false, false);
        ImageView iv1 = new ImageView();
        iv1.setImage(PlainClassBox);
        label.setContentDisplay(ContentDisplay.BOTTOM);
        label.setGraphic(new ImageView(PlainClassBox));
        HBox.setHgrow(label, Priority.ALWAYS);
        label.setMaxWidth(Double.MAX_VALUE);
       
        
        Button editName = new Button("Edit Name  ");
        Image TopClassBox = new Image("/images/TopClassBox.png", 35, 35, false, false);
        ImageView iv2 = new ImageView();
        iv2.setImage(TopClassBox);
        editName.setContentDisplay(ContentDisplay.RIGHT);
        editName.setGraphic(new ImageView(TopClassBox));
        editName.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(editName, Priority.ALWAYS);
        editName.setOnAction((ActionEvent e) -> {
        	TextArea text = classTextName;
        	applyActions(text);
        	revertActions(text);
        	label.setContentDisplay(ContentDisplay.BOTTOM);
            label.setGraphic(new ImageView(TopClassBox));

          
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

        Button editAttr = new Button("Edit attributes  ");
        Image MidClassBox = new Image("/images/MiddleClassBox.png", 35, 35, false, false);
        ImageView iv3 = new ImageView();
        iv3.setImage(MidClassBox);
        editAttr.setContentDisplay(ContentDisplay.RIGHT);
        editAttr.setGraphic(new ImageView(MidClassBox));
        editAttr.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(editAttr, Priority.ALWAYS);
        editAttr.setOnAction((ActionEvent e) -> {
            TextArea text = classMethods;
            applyActions(text);
            revertActions(text);
            label.setContentDisplay(ContentDisplay.BOTTOM);
            label.setGraphic(new ImageView(MidClassBox));
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

        Button editOps = new Button("Edit operations  ");
        Image BottomClassBox = new Image("/images/BottomClassBox.png", 35, 35, false, false);
        ImageView iv4 = new ImageView();
        iv4.setImage(BottomClassBox);
        editOps.setContentDisplay(ContentDisplay.RIGHT);
        editOps.setGraphic(new ImageView(BottomClassBox));
        editOps.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(editOps, Priority.ALWAYS);
        editOps.setOnAction((ActionEvent e) -> {
            TextArea text = classFunctions;
            applyActions(text);
            revertActions(text);
            label.setContentDisplay(ContentDisplay.BOTTOM);
            label.setGraphic(new ImageView(BottomClassBox));
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
        editName.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        editName.setId("selectedButton");
                        editAttr.setId("");
                        editOps.setId("");
                        classTextName.setId("focusedBox");
                    }
                });
        editAttr.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        editAttr.setId("selectedButton");
                        editOps.setId("");
                        editName.setId("");
                        classMethods.setId("focusedBox");
                        
                    }
                });
        
        editOps.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        editOps.setId("selectedButton");
                        editAttr.setId("");
                        editName.setId("");
                        classFunctions.setId("focusedBox");
                    }
                });
        
         Button deleteB = new Button("Delete  ");
        Image deleteClosed = new Image("/images/TrashCanClosed.png", 35, 35, false, false); 
        Image deleteOpen = new Image("/images/TrashCanOpen.png", 35, 35, false, false);
        ImageView iv5 = new ImageView();
        iv5.setImage(deleteClosed);
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

        HBox.setHgrow(label, Priority.ALWAYS);
        HBox.setHgrow(editName, Priority.ALWAYS);
        HBox.setHgrow(editOps, Priority.ALWAYS);
        HBox.setHgrow(editAttr, Priority.ALWAYS);
        HBox.setHgrow(deleteB, Priority.ALWAYS);
        h.getChildren().addAll(label, editName, editAttr, editOps, deleteB); 
       
    }
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(this.getTranslateX());
        out.writeDouble(this.getTranslateY());
        out.writeUTF(classTextName.getText());
        out.writeUTF(classMethods.getText());
        out.writeUTF(classFunctions.getText());
        out.writeDouble(this.getHeight());
        out.writeDouble(this.getWidth());
        
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setTranslateX(in.readDouble());
        this.setTranslateY(in.readDouble());
        classTextName.setText( in.readUTF());
        classMethods.setText(in.readUTF());
        classFunctions.setText(in.readUTF());
        this.setHeight(in.readDouble());
        this.setWidth(in.readDouble());

    }


}
