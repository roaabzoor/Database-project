package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Searchpet {

    @FXML
    public AnchorPane searchepet;
    public void goTomeue() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("menu.fxml"));
        searchepet.getChildren().setAll(root);
    }

    public void goTopet() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("Pet.fxml"));
        searchepet.getChildren().setAll(root);
    }
}
