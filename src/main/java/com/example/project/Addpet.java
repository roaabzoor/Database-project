package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Addpet {
    @FXML
    public AnchorPane adddpet;

    public void goToLogin1() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("Pet.fxml"));
        adddpet.getChildren().setAll(root);
    }

    public void goTomenu() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("menu.fxml"));
        adddpet.getChildren().setAll(root);
    }

}
