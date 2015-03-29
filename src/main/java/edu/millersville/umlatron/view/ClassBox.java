package edu.millersville.umlatron.view;

import java.util.ArrayList;

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
public class ClassBox extends VBox implements AnchorPoints{

    private double initX;
    private double initY;
    private int height, width;
    private int anchorCount;
    private Point2D[] anchorPoints;
    private ArrayList<String> pointTypes;
    private ArrayList<UMLLine> lines;
    private Point2D dragAnchor;
    private String name = "Enter A Class Name Here";
    private String methods = "Enter Methods Here";
    private String functions = "Enter Functions Here";
    private boolean clickedOn = false;

    @SuppressWarnings("restriction")
    public ClassBox(double x, double y) {
        super();
        //TODO
        //width = ?
        //height = ?
        anchorCount = 4;
        anchorPoints = new Point2D[anchorCount];
        pointTypes = new ArrayList<String>();
        lines = new ArrayList<UMLLine>();
        setAnchorPoints(x, y);
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
                updateXAnchorPoints(newXPosition);
                
                for (int i = 0; i < lines.size(); ++i)
                {
                	lines.get(i).updateAnchorPoints();
                	if (pointTypes.get(i).equals("start"))
                	{
                		lines.get(i).setStartX(anchorPoints[lines.get(i).getAnchorPoint1Int()].getX());
                	}
                	if (pointTypes.get(i).equals("end"))
                	{
                		lines.get(i).setEndX(anchorPoints[lines.get(i).getAnchorPoint2Int()].getX());
                	}
                }
            }
            if ((newYPosition >= event.getY()) && (newYPosition <= this.sceneProperty().get().getHeight() - (event.getY() + heightProperty().getValue()))) {
                setTranslateY(newYPosition);
                updateYAnchorPoints(newYPosition);
                for (int i = 0; i < lines.size(); ++i)
                {
                	lines.get(i).updateAnchorPoints();
                	if (pointTypes.get(i).equals("start"))
                	{
                		lines.get(i).setStartY(anchorPoints[lines.get(i).getAnchorPoint1Int()].getY());
                	}
                	if (pointTypes.get(i).equals("end"))
                	{
                		lines.get(i).setEndY(anchorPoints[lines.get(i).getAnchorPoint2Int()].getY());
                	}
                }
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

	@Override
	public void setAnchorPoints(double x, double y) {
		anchorPoints[0] = new Point2D(x, y + (height / 2)); 		//left
        anchorPoints[1] = new Point2D(x + (width / 2), y);  		//top
        anchorPoints[2] = new Point2D(x + width, y + (height / 2)); //right
        anchorPoints[3] = new Point2D(x + (width / 2), y + height); //bottom
	}

	@Override
	public void updateXAnchorPoints(double x) {
		anchorPoints[0] = new Point2D(x, anchorPoints[0].getY());
    	anchorPoints[1] = new Point2D(x + (width / 2), anchorPoints[1].getY());
    	anchorPoints[2] = new Point2D(x + width, anchorPoints[2].getY());
    	anchorPoints[3] = new Point2D(x + (width / 2), anchorPoints[3].getY());
	}

	@Override
	public void updateYAnchorPoints(double y) {
		anchorPoints[0] = new Point2D(anchorPoints[0].getX(), y + (height / 2));
    	anchorPoints[1] = new Point2D(anchorPoints[1].getX(), y);
    	anchorPoints[2] = new Point2D(anchorPoints[2].getX(), y + (height / 2));
    	anchorPoints[3] = new Point2D(anchorPoints[3].getX(), y + height);
	}

	@Override
	public Point2D getAnchorPoint(int i) {
		if (i < anchorPoints.length)
		{
			return anchorPoints[i];
		}
		else
		{
			return null;
		}
	}

	@Override
	public int getAnchorCount() {
		return anchorCount;
	}

	@Override
	public void addLineType(String str) {
		pointTypes.add(str);
	}

	@Override
	public void addLine(UMLLine line) {
		lines.add(line);
	}

	@Override
	public void setInitX(double d) {
		initX = d;
	}

	@Override
	public void setInitY(double d) {
		initY = d;
	}

	@Override
	public double getInitX() {
		return initX;
	}

	@Override
	public double getInitY() {
		return initY;
	}
}
