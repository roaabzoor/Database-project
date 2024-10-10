package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SearchVeterinarian {

    @FXML
    public AnchorPane Searchveterinarian;
    public void goToLogin1() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("Veterinarian.fxml"));
        Searchveterinarian.getChildren().setAll(root);
    }

    public void goTomenu() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("menu.fxml"));
        Searchveterinarian.getChildren().setAll(root);
    }
}
