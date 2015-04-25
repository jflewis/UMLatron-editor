package edu.millersville.umlatron;

import edu.millersville.umlatron.controller.UmlatronController;
import edu.millersville.umlatron.model.SelectState;
import edu.millersville.umlatron.model.UmlModel;
import edu.millersville.umlatron.model.ViewState;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.Rule;

/**
 *
 * @author Bill Barr
 */
public class CheckCorrectToggledTest {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Test
    public void checkInitialStateIsSelect() {
        UmlatronController controller = new UmlatronController(new Stage());
        UmlModel model = controller.getModel();
        assertEquals(model.getSelectStateProperty().get(), SelectState.SELECT);
    }

    @Test
    public void checkIfSelectIsToggled() {
        UmlatronController controller = new UmlatronController(new Stage());
        UmlModel model = controller.getModel();
        HBox toggleButtons = controller.getView().getToggleButtons();
        ToggleButton tb = (ToggleButton) toggleButtons.getChildren().get(0);
        tb.fire();
        tb.setSelected(true);
        assertEquals(model.getSelectStateProperty().get(), SelectState.SELECT);
    }

    @Test
    public void checkIfClassboxIsToggled() {
        UmlatronController controller = new UmlatronController(new Stage());
        UmlModel model = controller.getModel();
        HBox toggleButtons = controller.getView().getToggleButtons();
        ToggleButton tb = (ToggleButton) toggleButtons.getChildren().get(1);
        tb.fire();
        tb.setSelected(true);
        assertEquals(model.getSelectStateProperty().get(), SelectState.CLASSBOX);

    }

    @Test
    public void checkIfLineIsToggled() {
        UmlatronController controller = new UmlatronController(new Stage());
        UmlModel model = controller.getModel();
        HBox toggleButtons = controller.getView().getToggleButtons();
        ToggleButton tb = (ToggleButton) toggleButtons.getChildren().get(2);
        tb.fire();
        tb.setSelected(true);
        assertEquals(model.getSelectStateProperty().get(), SelectState.LINE);
    }

    @Test
    public void checkIfAssociationIsToggled() {
        UmlatronController controller = new UmlatronController(new Stage());
        UmlModel model = controller.getModel();
        HBox toggleButtons = controller.getView().getToggleButtons();
        ToggleButton tb = (ToggleButton) toggleButtons.getChildren().get(3);
        tb.fire();
        tb.setSelected(true);
        assertEquals(model.getSelectStateProperty().get(), SelectState.ASSOCIATION);
    }

    @Test
    public void checkIfGeneralizationIsToggled() {
        UmlatronController controller = new UmlatronController(new Stage());
        UmlModel model = controller.getModel();
        HBox toggleButtons = controller.getView().getToggleButtons();
        ToggleButton tb = (ToggleButton) toggleButtons.getChildren().get(4);
        tb.fire();
        tb.setSelected(true);
        assertEquals(model.getSelectStateProperty().get(), SelectState.GENERALIZATION);
    }

    @Test
    public void useCaseCheckIfSelectIsToggled() {
        UmlatronController controller = new UmlatronController(new Stage());
        UmlModel model = controller.getModel();
        controller.getModel().setViewState(ViewState.USE_CASE_UML);
        HBox toggleButtons = controller.getView().getToggleButtons();
        ToggleButton tb = (ToggleButton) toggleButtons.getChildren().get(0);
        tb.fire();
        tb.setSelected(true);
        assertEquals(model.getSelectStateProperty().get(), SelectState.SELECT);
    }
    
    @Test
    public void useCaseCheckIfUserIsToggled() {
        UmlatronController controller = new UmlatronController(new Stage());
        UmlModel model = controller.getModel();
        controller.getModel().setViewState(ViewState.USE_CASE_UML);
        HBox toggleButtons = controller.getView().getToggleButtons();
        ToggleButton tb = (ToggleButton) toggleButtons.getChildren().get(1);
        tb.fire();
        tb.setSelected(true);
        assertEquals(model.getSelectStateProperty().get(), SelectState.USER);
    }
    
    @Test
    public void useCaseCheckIfUseCaseIsToggled() {
        UmlatronController controller = new UmlatronController(new Stage());
        UmlModel model = controller.getModel();
        controller.getModel().setViewState(ViewState.USE_CASE_UML);
        HBox toggleButtons = controller.getView().getToggleButtons();
        ToggleButton tb = (ToggleButton) toggleButtons.getChildren().get(2);
        tb.fire();
        tb.setSelected(true);
        assertEquals(model.getSelectStateProperty().get(), SelectState.USE_CASE);
    }
    
    @Test
    public void useCaseCheckIfLineIsToggled() {
        UmlatronController controller = new UmlatronController(new Stage());
        UmlModel model = controller.getModel();
        controller.getModel().setViewState(ViewState.USE_CASE_UML);
        HBox toggleButtons = controller.getView().getToggleButtons();
        ToggleButton tb = (ToggleButton) toggleButtons.getChildren().get(3);
        tb.fire();
        tb.setSelected(true);
        assertEquals(model.getSelectStateProperty().get(), SelectState.LINE);
    }
}
