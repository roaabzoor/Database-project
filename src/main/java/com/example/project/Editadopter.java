package com.example.project;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Editadopter {
    public AnchorPane searchadopter ;
    public void gotoaddadopter() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("addAdopter.fxml"));
        searchadopter.getChildren().setAll(root);
    }

}
