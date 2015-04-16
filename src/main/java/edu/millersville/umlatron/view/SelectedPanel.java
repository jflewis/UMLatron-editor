/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.millersville.umlatron.view;

import javafx.scene.layout.HBox;

/**
 * Will be used for the visitor pattern/maybe
 * @author John Lewis
 */
public interface SelectedPanel {
    
    /**
     * all objects implementing this will be able to create a currently selected panel
     * @return a selected panel
     */
     void createAndGeneratePanel(HBox h);
         
}
