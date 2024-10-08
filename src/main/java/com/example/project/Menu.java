package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
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
    public void goToLogin() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        menu.getChildren().setAll(root);
    }
    @FXML
    public AnchorPane pet;
    public void goToLogin1() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("menu.fxml"));
        pet.getChildren().setAll(root);
    }

    public MediaView mv=new MediaView();
    public Button btnOpen;
    MediaPlayer mediaPlayer;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

         String vu = "C:\\Users\\hp\\IdeaProjects\\Database-projectt\\src\\main\\resources\\com\\example\\project\\vid\\the final video.mp4";
         Media media = new Media(new File(vu).toURI().toString());
         mediaPlayer = new MediaPlayer(media);


         mv.setMediaPlayer(mediaPlayer);

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
