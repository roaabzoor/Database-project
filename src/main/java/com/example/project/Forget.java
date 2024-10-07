package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Forget implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HelloApplication.s.setWidth(460);
        HelloApplication.s.setHeight(380);
    }

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
