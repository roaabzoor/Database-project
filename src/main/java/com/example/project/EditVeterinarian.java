package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class EditVeterinarian {

    @FXML
    public AnchorPane editveterinarian;
    public void goToLogin1() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("Veterinarian.fxml"));
        editveterinarian.getChildren().setAll(root);
    }

    public void goTomenu() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("menu.fxml"));
        editveterinarian.getChildren().setAll(root);
    }
}
