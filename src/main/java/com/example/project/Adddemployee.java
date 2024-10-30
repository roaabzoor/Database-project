package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class Adddemployee {
    @FXML
    public AnchorPane adddemployee;

    public void goToLogin1() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("add_employee.fxml"));
        adddemployee.getChildren().setAll(root);
    }

    public void goTomenu() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("menu.fxml"));
        adddemployee.getChildren().setAll(root);
    }

}
