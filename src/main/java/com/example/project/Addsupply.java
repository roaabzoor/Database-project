package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Addsupply {
    @FXML
    public AnchorPane addsupplies;
    public void goTosupply() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("Supplies.fxml"));
        addsupplies.getChildren().setAll(root);
    }
}
