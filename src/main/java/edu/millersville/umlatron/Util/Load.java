/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.millersville.umlatron.Util;

import edu.millersville.umlatron.view.EditPane;
import edu.millersville.umlatron.view.UMLLine;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import javafx.scene.Node;

/**
 *
 * @author John
 */
public class Load {

    public Load() {

    }

    public void load(EditPane pane) {
        try {
            FileInputStream fileIn = new FileInputStream("/tmp/employee.uml");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Node node = null;
            while (in != null) {
                try {
                    Node n = (Node) in.readObject();
                    if (n instanceof UMLLine) {
                        pane.getChildren().add(((UMLLine) n).createLineFromLoad());
                    } else {
                        pane.getChildren().add(n);
                    }
                } catch (EOFException exc) {
                    in.close();
                    break;
                }
            }
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("class not found");
            c.printStackTrace();
        }
    }

}
