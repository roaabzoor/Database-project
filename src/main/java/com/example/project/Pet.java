package com.example.project;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Pet {
    public AnchorPane pet;
    public void goToLogin1() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("menu.fxml"));
        pet.getChildren().setAll(root);
    }
    public void goToaddpet() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("addpet.fxml"));
        pet.getChildren().setAll(root);
    }
    public void goToeditpet() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("editpet.fxml"));
        pet.getChildren().setAll(root);
    }
   /*
    }
    public void goTosearchpet() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("searchVeterinarian.fxml"));
        pet.getChildren().setAll(root);
    }
    public void goToeditpet() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("editVeterinarian.fxml"));
        pet.getChildren().setAll(root);
    }*/
}
