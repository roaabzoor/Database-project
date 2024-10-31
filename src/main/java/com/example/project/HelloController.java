package com.example.project;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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

                    // Check if the user is an admin or not
                    if ("admin".equals(rs.getString("jopposition"))) {
                        homeController.setButtonVisibility(true); // Show buttons for admin
                    } else if ("notadmin".equals(rs.getString("jopposition"))) {
                        homeController.setButtonVisibility(false); // Hide buttons for not admin
                    }

                    Stage window = (Stage) loginpane.getScene().getWindow();
                    window.setScene(new Scene(menuRoot));
                    window.setWidth(720);
                    window.setHeight(490);
                    window.centerOnScreen();
                    return;  // Exit after successful login
                }

            }

         /*   if (!userFound) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid email or password.", ButtonType.OK);
                alert.showAndWait();
            }*/

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            // Close database resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
