package com.example.project;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.*;
public class Editrecord {
    @FXML
    public AnchorPane editrecord;

    @FXML
    private ComboBox<String> diagnosis;

    @FXML
    private JFXTextField dosage1;

    @FXML
    private JFXTextField recodid;

    @FXML
    private JFXTextField treatment1;

    @FXML
    private JFXComboBox<String> vaccinationstatu;

    // Initialize method to add options to both ComboBoxes
    public void initialize() {
        diagnosis.getItems().addAll(
                "Healthy",
                "Infection",
                "Injury",
                "Chronic Illness",
                "Vaccination Required",
                "Routine Check-up",
                "Allergy"
        );

        vaccinationstatu.getItems().addAll(
                "Pending",
                "Completed",
                "Partially Completed",
                "Not Required"
        );
    }

    // Method to load medical record from the database
    public void loadMedicalRecord() throws IOException {
        if (recodid.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Medical Record ID must be entered.");
            return;
        }

        int rId;
        try {
            rId = Integer.parseInt(recodid.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Medical Record ID must be a valid integer.");
            return;
        }

        // Database connection
        database db = new database();
        try (Connection conn = DriverManager.getConnection(db.url, db.user, db.password)) {
            String sql = "SELECT * FROM medicalrecord WHERE recordid = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, rId);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    // Populate fields with retrieved data
                    diagnosis.setValue(rs.getString("diagnosis"));
                    dosage1.setText(rs.getString("dosage"));
                    treatment1.setText(rs.getString("treatment"));
                    vaccinationstatu.setValue(rs.getString("vaccination_status"));
                } else {
                    showAlert(Alert.AlertType.ERROR, "Medical Record not found with the given ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database error: " + e.getMessage());
        }
    }

    // Method to save updated medical record to the database
    public void saveMedicalRecord() {
        if (diagnosis.getValue() == null || dosage1.getText().isEmpty() || treatment1.getText().isEmpty() || vaccinationstatu.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "All fields must be filled out.");
            return;
        }

        // Retrieve field values
        String diagnosis1 = diagnosis.getValue();
        String dosage = dosage1.getText();
        String treatment = treatment1.getText();
        String vaccinationStatus = vaccinationstatu.getValue();

        int rId;
        try {
            rId = Integer.parseInt(recodid.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Medical Record ID must be a valid integer.");
            return;
        }

        // Database connection
        database db = new database();
        try (Connection conn = DriverManager.getConnection(db.url, db.user, db.password)) {
            String sql = "UPDATE medicalrecord SET diagnosis = ?, dosage = ?, treatment = ?, vaccination_status = ? WHERE recordid = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, diagnosis1);
                pstmt.setString(2, dosage);
                pstmt.setString(3, treatment);
                pstmt.setString(4, vaccinationStatus);
                pstmt.setInt(5, rId);

                pstmt.executeUpdate();
                showAlert(Alert.AlertType.INFORMATION, "Medical Record information updated successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database error: " + e.getMessage());
        }
    }

    // Method to show alerts for success and error messages
    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.setTitle(alertType == Alert.AlertType.ERROR ? "Input Error" : "Success");
        alert.setHeaderText(null);
        alert.showAndWait();
    }




public void goTomenu() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("menu.fxml"));
        editrecord.getChildren().setAll(root);
    }
    public void goTomedical() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("midicalrecord.fxml"));
        editrecord.getChildren().setAll(root);
    }
}
