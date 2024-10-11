package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Editpet {

    @FXML
    public AnchorPane editepet;
    public void goTomeue() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("menu.fxml"));
        editepet.getChildren().setAll(root);
    }

    public void goTopet() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("Pet.fxml"));
        editepet.getChildren().setAll(root);
    }
}