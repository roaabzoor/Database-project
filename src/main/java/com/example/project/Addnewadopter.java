package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Addnewadopter {
    @FXML
    public AnchorPane addnew ;
    public void gotomenu() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        addnew.getChildren().setAll(root);
    }



}
