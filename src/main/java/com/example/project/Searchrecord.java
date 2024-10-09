package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Searchrecord {
    @FXML
    public AnchorPane searchrecord;
    public void goTomenu() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("menu.fxml"));
        searchrecord.getChildren().setAll(root);
    }
    public void goTomidical() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("midicalrecord.fxml"));
        searchrecord.getChildren().setAll(root);
    }
}
