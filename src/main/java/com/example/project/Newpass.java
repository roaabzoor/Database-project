package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Newpass {
    @FXML
    public AnchorPane newpass;
    public void gotologin() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("login.fxml"));
        newpass.getChildren().setAll(root);
    }
}
