/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.millersville.umlatron.Util;

import edu.millersville.umlatron.controller.UmlatronController;
import edu.millersville.umlatron.model.ViewState;
import edu.millersville.umlatron.view.ClassBox;
import edu.millersville.umlatron.view.EditPane;
import edu.millersville.umlatron.view.UseCase;
import edu.millersville.umlatron.view.User;
import edu.millersville.umlatron.view.umlLines.UMLLine;
import edu.millersville.umlatron.view.umlRecursiveLines.UMLRecursiveLine;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author John Lewis
 * This class wraps all file operation logic in a single place
 */
public class FileOperations {
    
    EditPane pane;
    Stage mainStage;
    File currentFile;
    UmlatronController controller;
    
    /**
     * The constructor for this object
     * @param pane The EditPane were objects are located at
     * @param mainStage The JavaFx stage used for presenting the file chooser
     */
    public FileOperations(EditPane pane,UmlatronController controller){
        this.pane = pane;
        this.mainStage = controller.stage;
        this.controller = controller;
    }
    
    
    
    /**
     * The open operation.
     * Prompts a user with a file chooser to open a saved  project
     */
    public void open(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open UML File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("UML design", "*.uml"));
        File selectedFile = fileChooser.showOpenDialog(mainStage);
        if (selectedFile != null) {
            try {
                FileInputStream fileIn = new FileInputStream(selectedFile);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                ViewState viewState = (ViewState)in.readObject();
                controller.getModel().setViewState(viewState);
                Node node = null;
                while (true) {
                    try {
                        Node n = (Node) in.readObject();
                        if (n instanceof UMLLine) {
                            pane.getChildren().add(((UMLLine) n).createLineFromLoad());
                        } else if (n instanceof UMLRecursiveLine) {
                            pane.getChildren().add(((UMLRecursiveLine) n).createLineFromLoad());
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
    
    /**
     * The save operation.
     * Prompts a user with a file chooser to save a current project
     */
    public void save(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save UML File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("UML design", "*.uml"));
        File selectedFile = fileChooser.showSaveDialog(mainStage);
        if (selectedFile != null) {
            try {
                FileOutputStream fileOut
                        = new FileOutputStream(selectedFile);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(controller.getModel().getViewStateProperty().getValue());
                for (Node n : pane.getChildren()) {
                    if (n instanceof ClassBox || n instanceof User || n instanceof UseCase) {
                        out.writeObject(n);
                    }
                }
                for (Node n : pane.getChildren()) {
                    if (n instanceof UMLLine) {
                        out.writeObject(n);
                    } else if (n instanceof UMLRecursiveLine) {
                        out.writeObject(n);
                    }
                }
                out.close();
                fileOut.close();
            } catch (IOException i) {
                i.printStackTrace();
            }
        }
    }
    
}
