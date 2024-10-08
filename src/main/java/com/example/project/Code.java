package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Code {
    @FXML
    public AnchorPane code;

    public void gotologin() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("login.fxml"));
        code.getChildren().setAll(root);

    }
    public void gotonewpass() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("newpass.fxml"));
        code.getChildren().setAll(root);
    }
}
