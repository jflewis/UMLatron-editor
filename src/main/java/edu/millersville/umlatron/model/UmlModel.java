
package edu.millersville.umlatron.model;

import java.io.File;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;

/**
 * The UmlModel class.
 * This class holds all the data and state of the program at any one time.
 * @author John Lewis
 */
public class UmlModel {
    
    private SimpleObjectProperty<SelectState> selectState;
    private SimpleObjectProperty<Node> currentlySelectedNode;
    private SimpleObjectProperty<ViewState> viewState;
    public boolean projectSaved = true;
    private File currentFile;
    
    /**
     * A public no argument constructor.
     * All values are private and only available  via get/set methods.
     * The above excludes the projectSaved variable
     */
    public UmlModel(){
        this.selectState = new SimpleObjectProperty();
        selectState.setValue(SelectState.SELECT);
        this.currentlySelectedNode = new SimpleObjectProperty();
        this.viewState = new SimpleObjectProperty(ViewState.CLASS_UML);
       
        
    }
    
    public void setSelectState(SelectState s){
        this.selectState.setValue(s);
    }
    
    public SimpleObjectProperty<SelectState> getSelectStateProperty(){
        return selectState;
    }
    
    public void setViewState(ViewState s){
        this.viewState.set(s);
    }
    
    public SimpleObjectProperty<ViewState> getViewStateProperty(){
        return viewState;
    }
    
    public SimpleObjectProperty<Node> getCurrentlySelectedNodeProperty(){
        return currentlySelectedNode;
    }
    
     
    
    
    
}
