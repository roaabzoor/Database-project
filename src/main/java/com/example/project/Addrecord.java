package com.example.project;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
    private JFXTextField diagnosis;

    @FXML
    private JFXTextField dosage;

    @FXML
    private JFXTextField petid;

    @FXML
    private JFXTextField treatment;

    @FXML
    private JFXTextField vaccinationstatus;

    @FXML
    private JFXTextField veteid;

    public void goTomenu() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("menu.fxml"));
        addrecord.getChildren().setAll(root);
    }
    public void goTomidical() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("midicalrecord.fxml"));
        addrecord.getChildren().setAll(root);
    }
    public void saverecord() throws IOException {
        if ( diagnosis.getText().isEmpty() ||
                dosage.getText().isEmpty() || petid.getText().isEmpty() ||
                treatment.getText().isEmpty() || vaccinationstatus.getText().isEmpty() ||
                veteid.getText().isEmpty())
        {

            showAlert(Alert.AlertType.ERROR, "All fields must be filled out.");
            return;
        }
        String diagnosisText = diagnosis.getText();
        String dosageText = dosage.getText();
        String petId = petid.getText();
        String treatmentText = treatment.getText();
        String vaccinationStatusText = vaccinationstatus.getText();
        String veterinarianId = veteid.getText();

        int petid1;
        try {
            petid1 = Integer.parseInt( petId);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Pet ID must be a valid integer.");
            return;
        }
        int veterinarianid1;
        try {
            veterinarianid1 = Integer.parseInt( veterinarianId);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Pet ID must be a valid integer.");
            return;
        }
        database db = new database();
        try (Connection conn = DriverManager.getConnection(db.url, db.user, db.password)) {
            String sql = "INSERT INTO medicalrecord (diagnosis, dosage, pet_id, treatment, vaccination_status, veterinarian_id) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, diagnosisText);
                pstmt.setString(2, dosageText);
                pstmt.setInt(3,petid1);
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
    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.setTitle(alertType == Alert.AlertType.ERROR ? "Input Error" : "Success");
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}