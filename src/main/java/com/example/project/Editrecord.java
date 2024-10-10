package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Editrecord {
    @FXML
    public AnchorPane editrecord;
    public void goTomenu() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("menu.fxml"));
        editrecord.getChildren().setAll(root);
    }
    public void goTomedical() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("midicalrecord.fxml"));
        editrecord.getChildren().setAll(root);
    }
}
