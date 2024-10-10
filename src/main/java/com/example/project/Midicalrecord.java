package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Midicalrecord {
    @FXML
    public AnchorPane midicalrecord;
    public void goTomenu() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("menu.fxml"));
        midicalrecord.getChildren().setAll(root);
    }
    public void goToaddrecord() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("addrecord.fxml"));
        midicalrecord.getChildren().setAll(root);
    }
    public void goTsearchrecord() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("searchrecord.fxml"));
        midicalrecord.getChildren().setAll(root);
    }
    public void goToeditrecord() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("editrecord.fxml"));
        midicalrecord.getChildren().setAll(root);
    }
}
