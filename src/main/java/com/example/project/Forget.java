package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Forget {
    @FXML
    public AnchorPane forget;
    public void gotologin() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("login.fxml"));
        forget.getChildren().setAll(root);
    }
    @FXML
    public AnchorPane code;
    public void gotologin2() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("code.fxml"));
        forget.getChildren().setAll(root);
    }

}
