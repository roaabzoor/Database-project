package com.example.project;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
public class Searchpet {

    @FXML
    private AnchorPane searchepet;
    @FXML
    private ComboBox<String> adoptionstatus;

    @FXML
    private JFXTextField age;

    @FXML
    private DatePicker arrivaldate;

    @FXML
    private JFXTextField breed;

    @FXML
    private JFXTextField petid;

    @FXML
    private JFXTextField petname;

    @FXML
    private JFXTextField species;

    public void initialize() {
        adoptionstatus.getItems().addAll("Adopted", "Available");
    }

    public void loadPet() throws IOException, SQLException {
        if (petid.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Pet ID must be entered.");
            return;
        }

        int petId;
        try {
            petId = Integer.parseInt(petid.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Pet ID must be a valid integer.");
            return;
        }

        // Database connection
        database db = new database();
        try (Connection conn = DriverManager.getConnection(db.url, db.user, db.password)) {
            String sql = "SELECT * FROM pet WHERE petid = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, petId);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    // Populate fields with retrieved data
                    petname.setText(rs.getString("petname"));
                    age.setText(String.valueOf(rs.getInt("age")));
                    arrivaldate.setValue(rs.getDate("admissiondate").toLocalDate());
                    breed.setText(rs.getString("breed"));
                    species.setText(rs.getString("species"));
                    adoptionstatus.setValue(rs.getString("adoption_status"));
                } else {
                    showAlert(Alert.AlertType.ERROR, "Pet not found with the given ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database error: " + e.getMessage());
        }
    }

    public void savePet() throws IOException, SQLException {
        if (petname.getText().isEmpty() || age.getText().isEmpty() || breed.getText().isEmpty() ||
                species.getText().isEmpty() || adoptionstatus.getValue() == null || arrivaldate.getValue() == null) {

            showAlert(Alert.AlertType.ERROR, "All fields must be filled out.");
            return;
        }

        // Retrieve and parse field values
        String petName = petname.getText();
        String breedText = breed.getText();
        String speciesText = species.getText();
        String adoptionStatus = adoptionstatus.getValue();
        LocalDate arrivalDate = arrivaldate.getValue();

        int petId;
        int petAge;
        try {
            petId = Integer.parseInt(petid.getText());
            petAge = Integer.parseInt(age.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Pet ID and Age must be valid integers.");
            return;
        }

        // Database connection
        database db = new database();
        try (Connection conn = DriverManager.getConnection(db.url, db.user, db.password)) {
            String sql = "UPDATE pet SET petname = ?, age = ?, admissiondate = ?, breed = ?, species = ?, adoption_status = ? WHERE petid = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, petName);
                pstmt.setInt(2, petAge);
                pstmt.setDate(3, java.sql.Date.valueOf(arrivalDate));
                pstmt.setString(4, breedText);
                pstmt.setString(5, speciesText);
                pstmt.setString(6, adoptionStatus);
                pstmt.setInt(7, petId);

                pstmt.executeUpdate();
                showAlert(Alert.AlertType.INFORMATION, "Pet information updated successfully.");
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

















    public void goTomeue() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("menu.fxml"));
        searchepet.getChildren().setAll(root);
    }

    public void goTopet() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("Pet.fxml"));
        searchepet.getChildren().setAll(root);
    }
}
