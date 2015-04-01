package edu.millersville.umlatron.view;

import javafx.scene.Cursor;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;

/**
 * 
 * @author Matthew Hipszer
 *
 */
public class UMLLine extends Line {
	private double initX;
	private double initY;
	private Point2D startingAnchor;
	private Point2D endingAnchor;
	private int point1Int;
	private int point2Int;
	private Point2D dragAnchor1;
	private Point2D dragAnchor2;
	private AnchorPoint anchorPoint1;
	private AnchorPoint anchorPoint2;
	private static int lineCount;
	private int id;

	/**
	 * 
	 * @param a1
	 *            The AnchorPoint that the starting point of the line is
	 *            attached to.
	 * @param a2
	 *            The AnchorPoint that the ending point of the line is attached
	 *            to.
	 */
	public UMLLine(AnchorPoint a1, AnchorPoint a2) {
		super(a1.getAnchorPoint(0).getX(), a1.getAnchorPoint(0).getY(), a2
				.getAnchorPoint(0).getX(), a2.getAnchorPoint(0).getY());
		id = lineCount;
		++lineCount;

		anchorPoint1 = a1;
		anchorPoint2 = a2;

		// sets the anchorPoints
		updateAnchorPoints();

		setCursor(Cursor.OPEN_HAND);
		setStrokeWidth(2.0);

		/*
		 * setOnMousePressed((event) -> { initX = event.getSceneX(); initY =
		 * event.getSceneY();
		 * anchorPoint1.setInitX(anchorPoint1.getTranslateX());
		 * anchorPoint1.setInitY(anchorPoint1.getTranslateX()); dragAnchor1 =
		 * new Point2D(event.getSceneX(), event.getSceneY());
		 * anchorPoint2.setInitX(anchorPoint2.getTranslateX());
		 * anchorPoint2.setInitY(anchorPoint2.getTranslateX()); dragAnchor2 =
		 * new Point2D(event.getSceneX(), event.getSceneY()); event.consume();
		 * });
		 * 
		 * setOnMouseDragged((event) -> { double dragXBox1 = event.getSceneX() -
		 * dragAnchor1.getX(); double dragYBox1 = event.getSceneY() -
		 * dragAnchor1.getY(); double dragXBox2 = event.getSceneX() -
		 * dragAnchor2.getX(); double dragYBox2 = event.getSceneY() -
		 * dragAnchor2.getY();
		 * 
		 * double newXPositionB1 = anchorPoint1.getInitX() + dragXBox1; double
		 * newYPositionB1 = anchorPoint1.getInitY() + dragYBox1; double
		 * newXPositionB2 = anchorPoint2.getInitX() + dragXBox2; double
		 * newYPositionB2 = anchorPoint2.getInitY() + dragYBox2;
		 * 
		 * double dragX = event.getSceneX(); double dragY = event.getSceneY();
		 * 
		 * //calculate new position of the line double newXPosition1 =
		 * this.getStartX() - (initX - dragX); double newXPosition2 =
		 * this.getEndX() - (initX - dragX); double newYPosition1 =
		 * this.getStartY() - (initY - dragY); double newYPosition2 =
		 * this.getEndY() - (initY - dragY);
		 * 
		 * //Makes sure you don't drag the line off the screen if
		 * ((newXPosition1 >= 0) && (newXPosition2 >= 0) && (newXPosition1 <=
		 * this.sceneProperty().get().getWidth()) && (newXPosition2 <=
		 * this.sceneProperty().get().getWidth()) && (newXPositionB1 >=
		 * anchorPoint1.getX()) && (newXPositionB1 <=
		 * anchorPoint1.sceneProperty().get().getWidth() - ((anchorPoint1.getX()
		 * + anchorPoint1.widthProperty().getValue()))) && (newXPositionB2 >=
		 * anchorPoint2.getX()) && (newXPositionB2 <=
		 * anchorPoint2.sceneProperty().get().getWidth() - ((anchorPoint2.getX()
		 * + anchorPoint2.widthProperty().getValue())))) {
		 * this.setStartX(newXPosition1); this.setEndX(newXPosition2);
		 * anchorPoint1.setTranslateX(newXPositionB1);
		 * anchorPoint2.setTranslateX(newXPositionB2); initX = dragX; } if
		 * ((newYPosition1 >= 0) && (newYPosition2 >= 0) && (newYPosition1 <=
		 * 750) && (newYPosition2 <= 750) && (newYPositionB1 >=
		 * anchorPoint1.getY()) && (newYPositionB1 <=
		 * anchorPoint1.sceneProperty().get().getHeight() - (anchorPoint1.getY()
		 * + anchorPoint1.heightProperty().getValue())) && (newYPositionB2 >=
		 * anchorPoint2.getY()) && (newYPositionB2 <=
		 * anchorPoint2.sceneProperty().get().getHeight() - (anchorPoint2.getY()
		 * + anchorPoint2.heightProperty().getValue()))) {
		 * this.setStartY(newYPosition1); this.setEndY(newYPosition2);
		 * anchorPoint1.setTranslateY(newYPositionB1);
		 * anchorPoint2.setTranslateY(newYPositionB2); initY = dragY; }
		 * event.consume(); });
		 * 
		 * setOnMouseReleased((event) -> { event.consume(); });
		 */

		MenuItem delete = new MenuItem("delete");

		delete.setOnAction((event) -> {
			deleteSelf();
		});

		ContextMenu contextMenu = new ContextMenu(delete);

		setOnMouseClicked((event) -> {
			if (event.getButton() == MouseButton.SECONDARY) {
				System.out.println("hello");
				contextMenu.show(this, event.getScreenX(), event.getScreenY());
			} else {
				contextMenu.hide();
			}
		});
	}

	/**
     * 
     */
	public void updateAnchorPoints() {
		double min = 999999999;
		point1Int = 0;
		point2Int = 0;
		for (int i = 0; i < anchorPoint1.getAnchorCount(); ++i) {
			startingAnchor = new Point2D(anchorPoint1.getAnchorPoint(i).getX(),
					anchorPoint1.getAnchorPoint(i).getY());
			for (int j = 0; j < anchorPoint2.getAnchorCount(); ++j) {
				endingAnchor = new Point2D(anchorPoint2.getAnchorPoint(j)
						.getX(), anchorPoint2.getAnchorPoint(j).getY());
				if (startingAnchor.distance(endingAnchor) < min) {
					min = startingAnchor.distance(endingAnchor);
					point1Int = i;
					point2Int = j;
				}
			}
		}
		this.setStartX(anchorPoint1.getAnchorPoint(point1Int).getX());
		this.setStartY(anchorPoint1.getAnchorPoint(point1Int).getY());
		this.setEndX(anchorPoint2.getAnchorPoint(point2Int).getX());
		this.setEndY(anchorPoint2.getAnchorPoint(point2Int).getY());

	}

	/**
	 * 
	 * @return returns which anchor point the starting point of the line is
	 *         connected to.
	 */
	public int getAnchorPoint1Int() {
		return point1Int;
	}

	/**
	 * 
	 * @return returns which anchor point the ending point of the line is
	 *         connected to.
	 */
	public int getAnchorPoint2Int() {
		return point2Int;
	}

	public void deleteSelf() {
		anchorPoint1.deleteLine(id);
		anchorPoint2.deleteLine(id);
	}

	public int getIntId() {
		return id;
	}

	/**
	 * 
	 */
	public void toggleDashed() {
		if (this.getStrokeDashArray().isEmpty()) {
			this.getStrokeDashArray().addAll(5d, 5d, 5d, 5d);
		} else {
			this.getStrokeDashArray().clear();
		}
	}

}
