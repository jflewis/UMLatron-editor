/*
 * 
 */
package edu.millersville.umlatron.model;

import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author John Lewis
 */
public class UmlModel {
    
    private SimpleObjectProperty<State> state;
    
    public UmlModel(){
        this.state = new SimpleObjectProperty();
        state.setValue(State.SELECT);
        
    }
    
    public void setState(State s){
        this.state.setValue(s);
    }
    
    public SimpleObjectProperty<State> getStateProperty(){
        return state;
    }
    
    
}
