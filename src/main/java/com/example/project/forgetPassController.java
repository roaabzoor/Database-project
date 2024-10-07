package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class forgetPassController {

    @FXML
    public AnchorPane forgetpasspane;
    public void goToLogin() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("Login-page.fxml"));
        forgetpasspane.getChildren().setAll(root);
    }
}
