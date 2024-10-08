package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AddAdopter {
    @FXML
    public AnchorPane adopter;
    public void gotologin() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("login.fxml"));
        adopter.getChildren().setAll(root);

    }
}
