package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Supplies {

    @FXML
    public AnchorPane supplies;
    public void goTomenu() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("menu.fxml"));
        supplies.getChildren().setAll(root);
    }
    public void goToaddsupply() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("addsupply.fxml"));
        supplies.getChildren().setAll(root);
    }
    public void goToeditsupply() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("editsupply.fxml"));
        supplies.getChildren().setAll(root);
    }
    public void goTosearchsupply() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("Searchsupply.fxml"));
        supplies.getChildren().setAll(root);
    }
}
