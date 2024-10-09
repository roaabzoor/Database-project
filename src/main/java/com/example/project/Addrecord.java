package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Addrecord {
    @FXML
    public AnchorPane addrecord;
    public void goTomenu() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("menu.fxml"));
        addrecord.getChildren().setAll(root);
    }
    public void goTomidical() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("midicalrecord.fxml"));
        addrecord.getChildren().setAll(root);
    }
}
