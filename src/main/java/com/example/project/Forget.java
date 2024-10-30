package com.example.project;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Forget implements Initializable {
    @FXML
    private JFXButton send;

    @FXML
    private JFXTextField sendemail;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public AnchorPane forget;
    public void gotologin() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("login.fxml"));
        forget.getChildren().setAll(root);
    }

    public void gotologin2() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("code.fxml"));
        forget.getChildren().setAll(root);
    }
























}
