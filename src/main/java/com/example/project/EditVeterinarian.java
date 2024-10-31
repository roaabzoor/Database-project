package com.example.project;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.*;


public class EditVeterinarian {

    @FXML
    public AnchorPane editveterinarian;


    @FXML
    private JFXTextField address2;

    @FXML
    private JFXTextField eamil2;

    @FXML
    private JFXTextField fname2;

    @FXML
    private JFXTextField lname2;

    @FXML
    private JFXTextField phonenum2;

    @FXML
    private JFXTextField salary2;

    @FXML
    private JFXTextField specialization2;

    @FXML
    private JFXTextField veterid2;


    public void loadVeterinarian() throws IOException, SQLException {
        if (veterid2.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Veterinarian ID must be entered.");
            return;
        }

        System.out.println("Veterinarian ID entered: " + veterid2.getText());

        int vId;
        try {
            vId = Integer.parseInt(veterid2.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Veterinarian ID must be a valid integer.");
            return;
        }

        // Database connection
        database db = new database();
        try (Connection conn = DriverManager.getConnection(db.url, db.user, db.password)) {
            System.out.println("Database connection successful.");

            String sql = "SELECT * FROM veterinarian WHERE veterinarianid = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, vId);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    // Populate fields with retrieved data
                    fname2.setText(rs.getString("fname"));
                    lname2.setText(rs.getString("lname"));
                    eamil2.setText(rs.getString("email"));
                    address2.setText(rs.getString("address"));
                    phonenum2.setText(String.valueOf(rs.getInt("phonenumber")));
                    salary2.setText(String.valueOf(rs.getDouble("salary")));
                    specialization2.setText(rs.getString("specialization"));
                    System.out.println("Veterinarian information loaded successfully.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Veterinarian not found with the given ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database error: " + e.getMessage());
        }
    }

    public void saveVeterinarian() throws IOException, SQLException {
        if ( fname2.getText().isEmpty() || lname2.getText().isEmpty() ||
                eamil2.getText().isEmpty() || phonenum2.getText().isEmpty() ||
                salary2.getText().isEmpty() || specialization2.getText().isEmpty()
                ||address2.getText().isEmpty())
        {
            showAlert(Alert.AlertType.ERROR, "All fields must be filled out.");
            return;
        }


        // Retrieve field values
        String firstName =  fname2.getText();
        String lastName = lname2.getText();
        String mail = eamil2.getText();
        String phoneStr = phonenum2.getText();
        String specializationStr = specialization2.getText();
        String addressStr = address2.getText();
        double Salary;

        // Validate salary
        try {
            Salary = Double.parseDouble(salary2.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Salary must be a valid number.");
            return;
        }

        // Validate phone number
        int phone;
        try {
            phone = Integer.parseInt(phoneStr);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Phone number must be a valid integer.");
            return;
        }

        int vId;
        try {
            vId = Integer.parseInt(veterid2.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Veterinarian ID must be a valid integer.");
            return;
        }

        // Database connection
        database db = new database();
        try (Connection conn = DriverManager.getConnection(db.url, db.user, db.password)) {
            String sql = "UPDATE veterinarian SET fname = ?, lname = ?, email = ?, phonenumber = ?,  salary = ?, specialization = ?,address = ? WHERE veterinarianid = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, firstName);
                pstmt.setString(2, lastName);
                pstmt.setString(3, mail);
                pstmt.setInt(4, phone);
                pstmt.setDouble(5, Salary);
                pstmt.setString(6, specializationStr);
                pstmt.setString(7, addressStr);  // Address should be set before vId
                pstmt.setInt(8, vId);  // Move vId to the last position as per SQL query



                pstmt.executeUpdate();
                showAlert(Alert.AlertType.INFORMATION, "Veterinarian information updated successfully.");
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



















    public void goToLogin1() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("Veterinarian.fxml"));
        editveterinarian.getChildren().setAll(root);
    }

    public void goTomenu() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("menu.fxml"));
        editveterinarian.getChildren().setAll(root);
    }
}
