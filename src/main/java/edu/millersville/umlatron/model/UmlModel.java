/*
 * 
 */
package edu.millersville.umlatron.model;

import edu.millersville.umlatron.view.Box;
import edu.millersville.umlatron.view.ClassBox;
import edu.millersville.umlatron.view.DiamondLine;
import edu.millersville.umlatron.view.UMLLine;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;

/**
 *
 * @author John Lewis
 */
public class UmlModel {
    
    private SimpleObjectProperty<State> state;
    private SimpleObjectProperty<Node> currentlySelectedNode;
    
    public UmlModel(){
        this.state = new SimpleObjectProperty();
        state.setValue(State.SELECT);
        
        
        this.currentlySelectedNode = new SimpleObjectProperty();
       
        
    }
    
    public void setState(State s){
        this.state.setValue(s);
    }
    
    public SimpleObjectProperty<State> getStateProperty(){
        return state;
    }
    
    public SimpleObjectProperty<Node> getCurrentlySelectedNodeProperty(){
        return currentlySelectedNode;
    }
    
    
    
}
