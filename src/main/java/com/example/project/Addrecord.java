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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class Addrecord {
    @FXML
    public AnchorPane addrecord;

    @FXML
    private ComboBox<String> diagnosis;

    @FXML
    private JFXTextField dosage;

    @FXML
    private JFXTextField petid;

    @FXML
    private JFXTextField treatment;

    @FXML
    private JFXComboBox<String> vaccinationstatus1;

    @FXML
    private JFXTextField veteid;

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

        vaccinationstatus1.getItems().addAll(
                "Pending",
                "Completed",
                "Partially Completed",
                "Not Required"
        );
    }

    public void goTomenu() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        addrecord.getChildren().setAll(root);
    }

    public void goTomidical() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("midicalrecord.fxml"));
        addrecord.getChildren().setAll(root);
    }

    // Method to save the medical record to the database
    public void saverecord() throws IOException {
        if (dosage.getText().isEmpty() || petid.getText().isEmpty() ||
                treatment.getText().isEmpty() || vaccinationstatus1.getValue() == null ||
                veteid.getText().isEmpty()) {

            showAlert(Alert.AlertType.ERROR, "All fields must be filled out.");
            return;
        }

        // Retrieve field values
        String diagnosisText = diagnosis.getValue();
        String dosageText = dosage.getText();
        String petId = petid.getText();
        String treatmentText = treatment.getText();
        String vaccinationStatusText = vaccinationstatus1.getValue();
        String veterinarianId = veteid.getText();

        // Validate pet ID as an integer
        int petid1;
        try {
            petid1 = Integer.parseInt(petId);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Pet ID must be a valid integer.");
            return;
        }

        // Validate veterinarian ID as an integer
        int veterinarianid1;
        try {
            veterinarianid1 = Integer.parseInt(veterinarianId);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Veterinarian ID must be a valid integer.");
            return;
        }

        // Database connection
        database db = new database();
        try (Connection conn = DriverManager.getConnection(db.url, db.user, db.password)) {
            String sql = "INSERT INTO medicalrecord (diagnosis, dosage, pet_id, treatment, vaccination_status, veterinarian_id) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, diagnosisText);
                pstmt.setString(2, dosageText);
                pstmt.setInt(3, petid1);
                pstmt.setString(4, treatmentText);
                pstmt.setString(5, vaccinationStatusText);
                pstmt.setInt(6, veterinarianid1);

                pstmt.executeUpdate();
                showAlert(Alert.AlertType.INFORMATION, "Medical record added successfully.");
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
}
