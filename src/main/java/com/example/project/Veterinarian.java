package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Veterinarian {
    @FXML
    public AnchorPane Veterinarian;

    public void goToLogin1() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("menu.fxml"));
        Veterinarian.getChildren().setAll(root);
    }
    public void goToaddVeterinarian() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("addVeterinarian.fxml"));
        Veterinarian.getChildren().setAll(root);
    }
    public void goTosearchVeterinarian() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("searchVeterinarian.fxml"));
        Veterinarian.getChildren().setAll(root);
    }
    public void goToeditVeterinarian() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("editVeterinarian.fxml"));
        Veterinarian.getChildren().setAll(root);
    }
}
