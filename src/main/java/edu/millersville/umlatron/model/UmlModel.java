/*
 * 
 */
package edu.millersville.umlatron.model;

import java.io.File;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;

/**
 *
 * @author John Lewis
 */
public class UmlModel {
    
    private SimpleObjectProperty<SelectState> selectState;
    private SimpleObjectProperty<Node> currentlySelectedNode;
    private SimpleObjectProperty<ViewState> viewState;
    public boolean projectSaved = true;
    private File currentFile;
    
    
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
