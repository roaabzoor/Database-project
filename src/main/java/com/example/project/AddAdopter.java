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
    public void gotoaddnewadopt() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("addnewadopter.fxml"));
        adopter.getChildren().setAll(root);

    }
    public void gotoaddeditadopt() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("editadopter.fxml"));
        adopter.getChildren().setAll(root);

    }
    public void goteditadoptinfo() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("EditAdopterinfo.fxml"));
        adopter.getChildren().setAll(root);

    }
}
