package com.example.project;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.*;

public class HelloController  {
    @FXML
    public AnchorPane loginpane;
    @FXML
    private JFXCheckBox checkbox;
    @FXML
    private JFXPasswordField login_pass;

    @FXML
    private JFXTextField username;
    @FXML
    private JFXTextField loginshow;
    String namee ;
    @FXML
    private Label invalid;
    private MediaPlayer successPlayer;

    public void initialize() {
        Media sound = new Media(getClass().getResource("/com/example/project/5iOSXjzSS2c.mp3").toString());
        successPlayer = new MediaPlayer(sound);
    }

    public void gotomenu() throws IOException {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try  {
            database b=new database();
            conn = DriverManager.getConnection(b.url,b.user,b.password  );
            System.out.println(conn.isClosed());
            if(conn!=null){
                System.out.println("here");
            }
            stmt = conn.createStatement();
            String sql = "SELECT * FROM employee";
            rs = stmt.executeQuery(sql);
            boolean userFound = false;

            while (rs.next()) {
                if (rs.getString("email").equals(username.getText()) &&
                        rs.getString("epassword").equals(login_pass.getText())) {

                    userFound = true;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
                    Parent menuRoot = loader.load();
                    Menu homeController = loader.getController();
                    homeController.setfirstname(rs.getString("fname"));

                    if ("admin".equals(rs.getString("jopposition"))) {
                        homeController.setButtonVisibility(true);
                    } else if ("notadmin".equals(rs.getString("jopposition"))) {
                        homeController.setButtonVisibility(false);
                    }

                    successPlayer.play();
                    showToast("You have logged in successfully!");

                    Stage window = (Stage) loginpane.getScene().getWindow();
                    window.setScene(new Scene(menuRoot));
                    window.setWidth(720);
                    window.setHeight(490);
                    window.centerOnScreen();
                    return;
                }

            }
           if (!userFound) {
               invalid.setText("Invalid email or password.");
               invalid.setVisible(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {

            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private void showToast(String message) {
        Stage toastStage = new Stage();
        Label toastLabel = new Label(message);
        toastLabel.setStyle("-fx-background-color: rgba(57, 28, 0, 0.5); -fx-padding: 10; -fx-font-size: 14; " +
                "-fx-border-radius: 5; -fx-background-radius: 5; -fx-text-fill: #ffffff;");

        Scene scene = new Scene(toastLabel);
        toastStage.setScene(scene);
        toastStage.setWidth(300);
        toastStage.setHeight(100);
        toastStage.setResizable(false);
        toastStage.setAlwaysOnTop(true);
        toastStage.setOpacity(0.9);
        toastStage.show();

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), toastLabel);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(event -> toastStage.close());

        fadeTransition.play();
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

    ////////////////////////////////////////////////////////
    public class myclass {
        public static String namer = "";
    }
    String getName (){
        return namee;
    }

}
