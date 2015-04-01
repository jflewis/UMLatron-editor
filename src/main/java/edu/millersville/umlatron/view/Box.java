/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.millersville.umlatron.view;

import java.util.ArrayList;

import edu.millersville.umlatron.model.LineType;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * 
 * @author John Lewis
 */
public class Box extends Rectangle implements AnchorPoint {

	private double initX;
	private double initY;
	private int width = 50;
	private int height = 50;
	private int anchorCount;
	private Point2D[] anchorPoints;
	private Point2D dragAnchor;
	private ArrayList<LineType> pointTypes;
	private ArrayList<UMLLine> lines;

	public Box(Color color, double x, double y) {
		super(50, 50, color);
		pointTypes = new ArrayList<LineType>();
		lines = new ArrayList<UMLLine>();
		setTranslateX(x);
		setTranslateY(y);
		setArcWidth(20);
		setArcHeight(20);
		anchorCount = 4;
		anchorPoints = new Point2D[anchorCount];
		setAnchorPoints(x, y);

		setCursor(Cursor.OPEN_HAND);

		setOnMouseDragged((event) -> {
			double dragX = event.getSceneX() - dragAnchor.getX();
			double dragY = event.getSceneY() - dragAnchor.getY();
			// calculate new position of the circle
			double newXPosition = initX + dragX;
			double newYPosition = initY + dragY;
			// if new position do not exceeds borders of the rectangle,
			// translate to this position
			if ((newXPosition >= getX())
					&& (newXPosition <= this.sceneProperty().get().getWidth()
							- (getX() + widthProperty().getValue()))) {
				setTranslateX(newXPosition);
				updateXAnchorPoints(newXPosition);
			}
			else if (newXPosition >= getX())
			{
				setTranslateX(this.sceneProperty().get().getWidth() - widthProperty().getValue());
				updateXAnchorPoints(this.sceneProperty().get().getWidth() - widthProperty().getValue());
			}
			else
			{
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
			if ((newYPosition >= getY())
					&& (newYPosition <= this.sceneProperty().get().getHeight()
							- (getY() + heightProperty().getValue()))) {
				setTranslateY(newYPosition);
				updateYAnchorPoints(newYPosition);
				
			}
 else if (newYPosition >= getY()) {
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

		setOnMousePressed((event) -> {
			// when mouse is pressed, store initial position
			initX = getTranslateX();
			initY = getTranslateY();
			dragAnchor = new Point2D(event.getSceneX(), event.getSceneY());
			event.consume();
		});

		MenuItem delete = new MenuItem("delete");
		delete.setOnAction(event -> {
			Pane pane = (Pane) this.getParent();
			for (int i = 0; i < lines.size(); ++i) {
				lines.get(i).deleteSelf();
			}
			pane.getChildren().remove(Box.this);
		});

		ContextMenu contextMenu = new ContextMenu(delete);

		setOnMouseClicked(event -> {
			// I need to be able to get the state here to check if it is line.
			if (event.getButton() == MouseButton.SECONDARY) {
				System.out.println("hello");
				contextMenu.show(this, event.getScreenX(), event.getScreenY());
			} else {
				contextMenu.hide();
			}
			event.consume();
		});

		setOnMouseReleased(event -> {
			event.consume();
		});
	}

	@Override
	public void setAnchorPoints(double x, double y) {
		anchorPoints[0] = new Point2D(x + (width / 2), y); // top
		anchorPoints[1] = new Point2D(x, y + (height / 2)); // left
		anchorPoints[2] = new Point2D(x + width, y + (height / 2)); // right
		anchorPoints[3] = new Point2D(x + (width / 2), y + height); // bottom
	}

	@Override
	public void updateXAnchorPoints(double x) {
		anchorPoints[0] = new Point2D(x + (width / 2), anchorPoints[0].getY());
		anchorPoints[1] = new Point2D(x, anchorPoints[1].getY());
		anchorPoints[2] = new Point2D(x + width, anchorPoints[2].getY());
		anchorPoints[3] = new Point2D(x + (width / 2), anchorPoints[3].getY());
	}

	@Override
	public void updateYAnchorPoints(double y) {
		anchorPoints[0] = new Point2D(anchorPoints[0].getX(), y);
		anchorPoints[1] = new Point2D(anchorPoints[1].getX(), y + (height / 2));
		anchorPoints[2] = new Point2D(anchorPoints[2].getX(), y + (height / 2));
		anchorPoints[3] = new Point2D(anchorPoints[3].getX(), y + height);
	}

	@Override
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
		for (int i = 0; i < lines.size(); ++i) {
			if (lines.get(i).getIntId() == id) {
				if (pane != null)
				{
					pane.getChildren().remove(lines.get(i));
				}
			}
		}
	}
}
