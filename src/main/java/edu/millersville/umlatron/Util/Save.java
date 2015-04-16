/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.millersville.umlatron.Util;

import edu.millersville.umlatron.view.ClassBox;
import edu.millersville.umlatron.view.EditPane;
import edu.millersville.umlatron.view.UMLLine;
import edu.millersville.umlatron.view.UMLRecursiveLine;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javafx.scene.Node;

/**
 *
 * @author John
 */
public class Save {

    EditPane pane;

    public Save(EditPane pane) {
        this.pane = pane;

        try {
            FileOutputStream fileOut
                    = new FileOutputStream("/tmp/employee.uml");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            for (Node n : pane.getChildren()) {
                if (n instanceof ClassBox) {
                    out.writeObject(n);
                }
            }
            for (Node n : pane.getChildren()) {
                if (n instanceof UMLLine) {
                    out.writeObject(n);
                }else if (n instanceof UMLRecursiveLine ){
                    out.writeObject(n);
                }
            }
            out.close();
                fileOut.close();
                System.out.printf("Serialized data is saved in /tmp/employee.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }

    }
}
