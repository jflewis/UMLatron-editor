package edu.millersville.umlatron.view;

import edu.millersville.umlatron.Util.AnchorInfo;

public interface AnchorPoint  {
    
    public AnchorInfo getNorthPoint();
    
    public AnchorInfo getSouthPoint();
    
    public AnchorInfo getEastPoint();
    
    public AnchorInfo getWestPoint();
    
    public void updateAnchorPoints();
    
    public void removeLine(UMLLine line);
    
    public void addLine(UMLLine line);
    
    
}

	