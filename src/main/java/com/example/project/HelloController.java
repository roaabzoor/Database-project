package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController  {
@FXML
public AnchorPane loginpane ;
public void gotomenu() throws IOException{
    AnchorPane root = FXMLLoader.load(getClass().getResource("menu.fxml"));
    loginpane.getChildren().setAll(root);
}
    public void gotoforget() throws IOException{
        AnchorPane root = FXMLLoader.load(getClass().getResource("forget.fxml"));
        loginpane.getChildren().setAll(root);
    }
    @FXML
    private Hyperlink forget;




}