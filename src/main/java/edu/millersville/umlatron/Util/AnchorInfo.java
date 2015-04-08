/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.millersville.umlatron.Util;

import javafx.beans.binding.DoubleBinding;

/**
 *
 * @author John
 */
public class AnchorInfo {
    private DoubleBinding x;
    private DoubleBinding y;
    
    public AnchorInfo(DoubleBinding x, DoubleBinding y){
        this.x = x;
        this.y = y;
    }
   
    
    public DoubleBinding getX(){
        return x;
    }
    
     public DoubleBinding getY(){
        return y;
    }
    
}
