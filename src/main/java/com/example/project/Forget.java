package com.example.project;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.UUID;

public class Forget implements Initializable {
    @FXML
    private JFXTextField sendemail;
    @FXML
    public AnchorPane forget;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void gotologin() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("login.fxml"));
        forget.getChildren().setAll(root);
    }

    public void gotologin2() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("code.fxml"));
        forget.getChildren().setAll(root);
    }
























}
