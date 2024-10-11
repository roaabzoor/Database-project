package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class EditEmployee {


    @FXML
    public AnchorPane editemployee;
    public void goToLogin() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("edit_employee.fxml"));
        editemployee.getChildren().setAll(root);
    }


    public void goToaddemployee() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("add_employee.fxml"));
        editemployee.getChildren().setAll(root);
    }

}
