package com.example.project;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController  {
@FXML
public AnchorPane loginpane ;
    @FXML
    private JFXCheckBox checkbox;
    @FXML
    private JFXPasswordField login_pass;
    @FXML
    private JFXTextField loginshow;
    public boolean isShown=false;
public void gotomenu() throws IOException{
    AnchorPane root = FXMLLoader.load(getClass().getResource("menu.fxml"));
    loginpane.getChildren().setAll(root);
}
    public void gotoforget() throws IOException{
        AnchorPane root = FXMLLoader.load(getClass().getResource("forget.fxml"));
        loginpane.getChildren().setAll(root);
    }
    public void togglePass() {
        if(checkbox.isSelected()){
            loginshow.setText(login_pass.getText());
            loginshow.setVisible(true);
            login_pass.setVisible(false);
        }
        else {
            login_pass.setText(loginshow.getText());
            loginshow.setVisible(false);
            login_pass.setVisible(true);
        }
    }

}