package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AddEmployee {

    @FXML
    public AnchorPane addemployee;
    public void goToLogin() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("menu.fxml"));
        addemployee.getChildren().setAll(root);
    }

    public void goToLogin1() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("menu.fxml"));
        addemployee.getChildren().setAll(root);
    }
    public void goToadddemployee() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("adddemployee.fxml"));
        addemployee.getChildren().setAll(root);
    }
    public void goToeditemployee() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("edit_employee.fxml"));
        addemployee.getChildren().setAll(root);
    }
}
