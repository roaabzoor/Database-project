package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AddVeterinarian {
    @FXML
    public AnchorPane addVeterinarian;

    public void goToLogin1() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("menu.fxml"));
        addVeterinarian.getChildren().setAll(root);
    }
    public void goToVeterinarian() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("Veterinarian.fxml"));
        addVeterinarian.getChildren().setAll(root);
    }
}
