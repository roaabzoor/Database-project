package com.example.project;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.desktop.UserSessionEvent;
import java.io.IOException;
import java.sql.*;

public class HelloController  extends database{
@FXML
public AnchorPane loginpane ;
    @FXML
    private JFXCheckBox checkbox;
    @FXML
    private JFXPasswordField login_pass;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXTextField loginshow;
    Connection conn;
    Statement stmt;
    ResultSet rs;

    public boolean isShown=false;
    String namee ;

    public void gotomenu() throws IOException, SQLException {
        try {
            // Establishing a database connection
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM public.employee";
            rs = stmt.executeQuery(sql);

            boolean userFound = false;  // Flag to check if user is found


            while (rs.next()) {
                // Check if email matches
                if (rs.getString("email").equals(username.getText())) {
                    // Check if password matches
                    if (rs.getString("password").equals(login_pass.getText())) {
                        userFound = true;  // User authenticated successfully
                        if (rs.getString("jopposition").equals("admin")) {
                            // Load the menu screen
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
                            Parent menuRoot = loader.load();
                            Menu homeController = loader.getController();
                            homeController.setfirstname(rs.getString("fname"));  // Set user's first name in menu

                            // Change scene to the menu
                            Stage window = (Stage) loginpane.getScene().getWindow();
                            window.setScene(new Scene(menuRoot));
                            window.centerOnScreen();
                            return;  // Exit method after successful login
                        }
                    }
                }
            }
            // Alert if user not found or credentials don't match
            if (!userFound) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid email or password.", ButtonType.OK);
                alert.showAndWait();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            // Clean up database resources
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }

   //AnchorPane root = FXMLLoader.load(getClass().getResource("menu.fxml"));
   // loginpane.getChildren().setAll(root);

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
