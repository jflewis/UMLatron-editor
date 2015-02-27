package edu.millersville.umlatron;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

//All dragging events work improperly if the mouse is moving too fast.
public class UMLLine extends Line {

    private double initX;
    private double initY;
    private boolean startingBoxContains = false;
    private boolean endingBoxContains = false;
    private double side = 5;

    public UMLLine(double x1, double y1, double x2, double y2) {
        super(x1, y1, x2, y2);
        setCursor(Cursor.OPEN_HAND);
        setStrokeWidth(2.0);

        setOnMousePressed((event) -> {
            initX = event.getSceneX();
            initY = event.getSceneY();
            startingBoxContains(initX, initY);
            endingBoxContains(initX, initY);
            event.consume();
        });

        setOnMouseDragged((event) -> {

            if (startingBoxContains) {
                this.setStartX(event.getSceneX());
                this.setStartY(event.getSceneY());
            } else if (endingBoxContains) {
                this.setEndX(event.getSceneX());
                this.setEndY(event.getSceneY());
            } else {
                double dragX = event.getSceneX();
                double dragY = event.getSceneY();

                //calculate new position of the line
                double newXPosition1 = this.getStartX() - (initX - dragX);
                double newXPosition2 = this.getEndX() - (initX - dragX);
                double newYPosition1 = this.getStartY() - (initY - dragY);
                double newYPosition2 = this.getEndY() - (initY - dragY);

                //Makes sure you don't drag the line off the screen
                if ((newXPosition1 >= 0) && (newXPosition2 >= 0) && (newXPosition1 <= this.sceneProperty().get().getWidth()) && (newXPosition2 <= this.sceneProperty().get().getWidth())) {
                    this.setStartX(newXPosition1);
                    this.setEndX(newXPosition2);
                    initX = dragX;
                }
                if ((newYPosition1 >= 0) && (newYPosition2 >= 0) && (newYPosition1 <= 750) && (newYPosition2 <= 750)) {
                    this.setStartY(newYPosition1);
                    this.setEndY(newYPosition2);
                    initY = dragY;
                }
            }
            event.consume();
        });

        setOnMouseReleased((event) -> {
            startingBoxContains = false;
            endingBoxContains = false;
            event.consume();
        });

        MenuItem delete = new MenuItem("delete");
        delete.setOnAction(event -> {
            Group group = (Group) this.getParent();
            group.getChildren().remove(this);
        });

        ContextMenu contextMenu = new ContextMenu(delete);

        setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                System.out.println("hello");
                contextMenu.show(this, event.getScreenX(), event.getScreenY());
            } else {
                contextMenu.hide();
            }
        });

    }

    private void startingBoxContains(double x, double y) {
        if (x < this.getStartX() - side || x > this.getStartX() + side || y < this.getStartY() - side || y > this.getStartY() + side) {
            startingBoxContains = false;
        } else {
            startingBoxContains = true;
        }
    }

    private void endingBoxContains(double x, double y) {
        if (x < this.getEndX() - side || x > this.getEndX() + side || y < this.getEndY() - side || y > this.getEndY() + side) {
            endingBoxContains = false;
        } else {
            endingBoxContains = true;
        }
    }

}
