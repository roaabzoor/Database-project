package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class loginController {
@FXML
public AnchorPane loginPane;
    public void goToForgetPage() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("ForgetPass-page.fxml"));
        loginPane.getChildren().setAll(root);
    }
}
