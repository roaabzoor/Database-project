package com.example.project;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddVeterinarian {
    @FXML
    public AnchorPane addVeterinarian;

    @FXML
    private Label invalid;

    @FXML
    private JFXTextField address;

    @FXML
    private JFXTextField eamil;

    @FXML
    private JFXTextField fname;

    @FXML
    private JFXTextField lname;

    @FXML
    private JFXTextField phonenum;

    @FXML
    private JFXTextField salary;

    @FXML
    private JFXTextField specialization;

    public void goToLogin1() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        addVeterinarian.getChildren().setAll(root);
    }

    public void goToVeterinarian() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("Veterinarian.fxml"));
        addVeterinarian.getChildren().setAll(root);
    }

    public void saveVeterinarian() throws IOException {
        if (fname.getText().isEmpty() || lname.getText().isEmpty() ||
                eamil.getText().isEmpty() || phonenum.getText().isEmpty() ||
                address.getText().isEmpty() || salary.getText().isEmpty()
                || specialization.getText().isEmpty()) {

            showAlert(Alert.AlertType.ERROR, "All fields must be filled out.");
            return;
        }
        String fname1 = fname.getText();
        String lname2 = lname.getText();
        String mail = eamil.getText();
        String phoneStr = phonenum.getText();
        String address1 = address.getText();
        String specialization1 = specialization.getText();
        int phone;

        if (phoneStr.isEmpty() || !phoneStr.matches("\\d+")) {
            invalid.setText("Invalid phone number.");
            invalid.setVisible(true);
            return;
        } else {
            invalid.setVisible(false);
            try {
                phone = Integer.parseInt(phoneStr);
            } catch (NumberFormatException e) {
                invalid.setText("Invalid phone number input.");
                invalid.setVisible(true);
                return;
            }
        }
        double empSalary;
        try {
            empSalary = Double.parseDouble(salary.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Salary must be a valid number.");
            return;
        }
            database db = new database();
            try (Connection conn = DriverManager.getConnection(db.url, db.user, db.password)) {
                String sql = "INSERT INTO veterinarian (fname, lname, email, phonenumber, address, salary, specialization) VALUES (?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, fname1);
                    pstmt.setString(2, lname2);
                    pstmt.setString(3, mail);
                    pstmt.setInt(4, phone);
                    pstmt.setString(5, address1);
                    pstmt.setDouble(6, empSalary);
                    pstmt.setString(7, specialization1);


                    pstmt.executeUpdate();
                    showAlert(Alert.AlertType.INFORMATION, "veterinarian added successfully.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Database error: " + e.getMessage());
            }
        }

        private void showAlert (Alert.AlertType alertType, String message){
            Alert alert = new Alert(alertType, message, ButtonType.OK);
            alert.setTitle(alertType == Alert.AlertType.ERROR ? "Input Error" : "Success");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }


