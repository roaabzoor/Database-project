package com.example.project;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.*;


public class EditAdopterinfo {
    public AnchorPane editadopterinfo ;

    @FXML
    private JFXTextField address3;

    @FXML
    private JFXTextField adopterid;

    @FXML
    private JFXTextField eamil3;

    @FXML
    private JFXTextField fname3;

    @FXML
    private JFXTextField lname3;

    @FXML
    private JFXTextField phonenum3;


    public void loadadopter() throws IOException, SQLException {
        if (adopterid.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Adopter ID must be entered.");
            return;
        }

        System.out.println("Adopter ID entered: " + adopterid.getText());

        int adopterid1;
        try {
            adopterid1 = Integer.parseInt(adopterid.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Adopter ID must be a valid integer.");
            return;
        }

        // Database connection
        database db = new database();
        try (Connection conn = DriverManager.getConnection(db.url, db.user, db.password)) {
            System.out.println("Database connection successful.");

            String sql = "SELECT * FROM adopter WHERE adopterid = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, adopterid1);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    // Populate fields with retrieved data
                    fname3.setText(rs.getString("fname"));
                    lname3.setText(rs.getString("lname"));
                    eamil3.setText(rs.getString("email"));
                    address3.setText(rs.getString("address"));
                    phonenum3.setText(String.valueOf(rs.getInt("contactnumber")));
                    System.out.println("Veterinarian information loaded successfully.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Adopter not found with the given ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database error: " + e.getMessage());
        }
    }

    public void saveadopter() throws IOException, SQLException {
        if ( fname3.getText().isEmpty() || lname3.getText().isEmpty() ||
                eamil3.getText().isEmpty() || phonenum3.getText().isEmpty()
                ||address3.getText().isEmpty())
        {
            showAlert(Alert.AlertType.ERROR, "All fields must be filled out.");
            return;
        }


        // Retrieve field values
        String firstName =  fname3.getText();
        String lastName = lname3.getText();
        String mail = eamil3.getText();
        String phoneStr = phonenum3.getText();
        String addressStr = address3.getText();


        // Validate phone number
        int phone;
        try {
            phone = Integer.parseInt(phoneStr);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Phone number must be a valid integer.");
            return;
        }

        int aId;
        try {
            aId = Integer.parseInt(adopterid.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Adopter ID must be a valid integer.");
            return;
        }

        // Database connection
        database db = new database();
        try (Connection conn = DriverManager.getConnection(db.url, db.user, db.password)) {
            String sql = "UPDATE adopter SET fname = ?, lname = ?, email = ?, contactnumber = ?,address = ? WHERE adopterid = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, firstName);
                pstmt.setString(2, lastName);
                pstmt.setString(3, mail);
                pstmt.setInt(4, phone);
                pstmt.setString(5, addressStr);  // Address should be set before vId
                pstmt.setInt(6, aId);  // Move vId to the last position as per SQL query



                pstmt.executeUpdate();
                showAlert(Alert.AlertType.INFORMATION, "Adopter information updated successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database error: " + e.getMessage());
        }
    }


    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.setTitle(alertType == Alert.AlertType.ERROR ? "Input Error" : "Success");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

















    public void gotoaddadopter() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("addAdopter.fxml"));
        editadopterinfo.getChildren().setAll(root);
    }
    public void gotomenu() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        editadopterinfo.getChildren().setAll(root);
    }
}
