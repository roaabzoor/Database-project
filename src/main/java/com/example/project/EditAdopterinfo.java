package com.example.project;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class EditAdopterinfo {
    public AnchorPane editadopterinfo ;
    public void gotoaddadopter() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("addAdopter.fxml"));
        editadopterinfo.getChildren().setAll(root);
    }
    public void gotomenu() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        editadopterinfo.getChildren().setAll(root);
    }
}
