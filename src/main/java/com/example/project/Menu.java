package com.example.project;

import com.jfoenix.controls.JFXTextField;
import eu.hansolo.toolbox.properties.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;

public class Menu implements Initializable {

    @FXML
    public AnchorPane menu;

    @FXML
    private Label firstname;

    @FXML
    private Button emplyeebtn;
    @FXML
    private Button veterinarinbtn;

    public void goToLogin() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("login.fxml"));
        menu.getChildren().setAll(root);
    }
    public AnchorPane employee;
    public void goToLogin1() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("add_employee.fxml"));
        menu.getChildren().setAll(root);
    }
    public void goToVeterinarian() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("Veterinarian.fxml"));
        menu.getChildren().setAll(root);
    }
    public void goTopet() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("Pet.fxml"));
        menu.getChildren().setAll(root);
    }
    public void gotoaddadpter() throws IOException{
        AnchorPane root = FXMLLoader.load(getClass().getResource("addAdopter.fxml"));
        menu.getChildren().setAll(root);
    }
    public void gotomidical() throws IOException{
        AnchorPane root = FXMLLoader.load(getClass().getResource("midicalrecord.fxml"));
        menu.getChildren().setAll(root);
    }
    public void gotosupplies() throws IOException{
        AnchorPane root = FXMLLoader.load(getClass().getResource("Supplies.fxml"));
        menu.getChildren().setAll(root);
    }


    public void setfirstname(String firstName) {
        firstname.setText(firstName);
    }

    public void setButtonVisibility(boolean isVisible) {
        emplyeebtn.setVisible(isVisible);
        veterinarinbtn.setVisible(isVisible);
    }
    public MediaView mv=new MediaView();

    MediaPlayer mediaPlayer;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        String resourcePath = "/com/example/project/vid/vedio.mp4";

        URL resourceUrl = getClass().getResource(resourcePath);

            Media media = new Media(resourceUrl.toExternalForm());
            mediaPlayer = new MediaPlayer(media);

            mv.setMediaPlayer(mediaPlayer);
        mv.setFitWidth(1000);
        mv.setFitHeight(380);

        // Maintain aspect ratio
        mv.setPreserveRatio(true);

        // StackPane to hold the MediaView and set alignment to center
        StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER);  // Ensures video is centered

    }
    public void openButton() {
        if ( mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.play();
        }
        else {
            mediaPlayer.play();
        }
    }


}
