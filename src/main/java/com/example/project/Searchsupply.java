package com.example.project;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Searchsupply {
    public AnchorPane searchsupply;
    public void goTomenu() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("Supplies.fxml"));
        searchsupply.getChildren().setAll(root);
    }
}
