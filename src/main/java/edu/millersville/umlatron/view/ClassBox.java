package edu.millersville.umlatron.view;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;

/**
 *
 * @author Greg Polhemus
 */
public class ClassBox extends VBox {

    private double initX;
    private double initY;
    private Point2D dragAnchor;
    private String name = "Enter A Class Name Here";
    private String methods = "Enter Methods Here";
    private String functions = "Enter Functions Here";
    private boolean clickedOn = false;

    @SuppressWarnings("restriction")
    public ClassBox(double x, double y) {
        super();
        setCursor(Cursor.OPEN_HAND);
        setTranslateX(x);
        setTranslateY(y);
        setStyle("-fx-border-style: solid;" + "-fx-border-width: 5;" + "-fx-border-color: black;");

        TextArea classTextName = new TextArea();
        classTextName.setPromptText(name);
        classTextName.setPrefRowCount(2);
        classTextName.setPrefColumnCount(16);
        classTextName.setWrapText(true);
        classTextName.setMouseTransparent(true);

 
	//ScrollBar scrollBarv = (Scrollbar)classTextName.lookup(".scroll-bar:vertical");
        TextArea classMethods = new TextArea();
        classMethods.setPromptText(methods);
        classMethods.setPrefRowCount(3);
        classMethods.setPrefColumnCount(16);
        classMethods.setWrapText(true);
        classMethods.setMouseTransparent(true);

        TextArea classFunctions = new TextArea();
        classFunctions.setPromptText(functions);
        classFunctions.setPrefRowCount(4);
        classFunctions.setPrefColumnCount(16);
        classFunctions.setWrapText(true);
        classFunctions.setMouseTransparent(true);

        getChildren().addAll(classTextName, classMethods, classFunctions);

        setOnMouseDragged((event) -> {

            double dragX = event.getSceneX() - dragAnchor.getX();
            double dragY = event.getSceneY() - dragAnchor.getY();
            double newXPosition = initX + dragX;
            double newYPosition = initY + dragY;

            if ((newXPosition >= event.getX()) && (newXPosition <= this.sceneProperty().get().getWidth() - ((event.getX() + widthProperty().getValue())))) {
                setTranslateX(newXPosition);
            }
            if ((newYPosition >= event.getY()) && (newYPosition <= this.sceneProperty().get().getHeight() - (event.getY() + heightProperty().getValue()))) {
                setTranslateY(newYPosition);
            }
            event.consume();
        });

        setOnMousePressed((event) -> {
            if (event.getClickCount() >= 2) {
                classTextName.setMouseTransparent(false);
                classFunctions.setMouseTransparent(false);
                classMethods.setMouseTransparent(false);

                System.out.println("Double Click");

            } else {

                getFocus(classTextName);
                classFunctions.setMouseTransparent(true);
                classMethods.setMouseTransparent(true);
			//classTextName.setMouseTransparent(true);
                //	classFunctions.setMouseTransparent(true);
                initX = getTranslateX();
                initY = getTranslateY();
                dragAnchor = new Point2D(event.getSceneX(), event.getSceneY());
                //.getScene().setCursor(Cursor.HAND);
                event.consume();
            }
        });

        classTextName.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                System.out.println("gotchaBitch");
                classMethods.setMouseTransparent(true);
                classTextName.setMouseTransparent(true);
                classFunctions.setMouseTransparent(true);
            } else {

            }
            event.consume();
        });

        MenuItem delete = new MenuItem("delete");
        delete.setOnAction(event -> {
            Group group = (Group) this.getParent();
            group.getChildren().remove(this);
        });

        ContextMenu contextMenu = new ContextMenu(delete);

        classTextName.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                //System.out.println("hello");
                contextMenu.show(this, event.getScreenX(), event.getScreenY() - 35);
            } else {
                contextMenu.hide();
            }
            event.consume();
        });
        
        classFunctions.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                //System.out.println("hello");
                contextMenu.show(this, event.getScreenX(), event.getScreenY() - 35);
            } else {
                contextMenu.hide();
            }
            event.consume();
        });
        
        classMethods.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                //System.out.println("hello");
                contextMenu.show(this, event.getScreenX(), event.getScreenY() - 35);
            } else {
                contextMenu.hide();
            }
            event.consume();
        });

        setOnMouseClicked(event -> {
            event.consume();
        });
    }

    public void getFocus(TextArea classTextName) {
        classTextName.setOnMouseClicked(event -> {
            classTextName.setMouseTransparent(true);
            event.consume();
        });

    }
}
