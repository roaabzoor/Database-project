package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private Hyperlink forget;

    @FXML
    private Button login;

    @FXML
    private TextField pass;

    @FXML
    private TextField user;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("osama");
    }

}