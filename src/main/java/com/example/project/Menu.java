package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Menu {
    @FXML
    public AnchorPane menu;
    public void gotologin() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("login.fxml"));
        menu.getChildren().setAll(root);
    }


}
