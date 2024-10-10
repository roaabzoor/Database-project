package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Editsupply {
    @FXML
    public AnchorPane editsupply;
    public void goTomenu() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("menu.fxml"));
        editsupply.getChildren().setAll(root);
    }
    public void goTosupply() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("Supplies.fxml"));
        editsupply.getChildren().setAll(root);
    }
}
