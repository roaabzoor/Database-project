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
    private JFXTextField adopterid;

    @FXML
    private JFXTextField employeeid;

    @FXML
    private DatePicker arrivaldate;

    @FXML
    private ComboBox<String> species;


    @FXML
    private JFXTextField petid;

    @FXML
    private JFXTextField petname;

    @FXML
    private ComboBox<String> breed;

    public void initialize() {
        adoptionstatus.getItems().addAll("Adopted", "Available");
        breed.getItems().addAll(
                "Labrador", "Beagle", "Bulldog", "Poodle", "Golden Retriever",
                "German Shepherd", "Rottweiler", "Yorkshire Terrier", "Dachshund", "Siberian Husky",
                "Persian", "Maine Coon", "Siamese", "Ragdoll", "Bengal",
                "Sphynx", "British Shorthair", "Scottish Fold", "American Shorthair", "Norwegian Forest"
        );
        species.getItems().addAll("Dog", "Cat");
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
                    petname.setText(rs.getString("petname"));
                    age.setText(String.valueOf(rs.getInt("age")));
                    arrivaldate.setValue(rs.getDate("admissiondate").toLocalDate());
                    breed.setValue(rs.getString("breed"));
                    species.setValue(rs.getString("species"));
                    adoptionstatus.setValue(rs.getString("adoption_status"));
                    adopterid.setText(String.valueOf(rs.getInt("adopter_id")));
                    employeeid.setText(String.valueOf(rs.getInt("employee_id")));

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
        if (petname.getText().isEmpty() || age.getText().isEmpty() || breed.getValue() == null ||
                species.getValue() == null || adoptionstatus.getValue() == null || arrivaldate.getValue() == null) {

            showAlert(Alert.AlertType.ERROR, "All fields must be filled out.");
            return;
        }

        String petName = petname.getText();
        String breedText = breed.getValue();
        String speciesText = species.getValue();
        String adoptionStatus = adoptionstatus.getValue();
        LocalDate arrivalDate = arrivaldate.getValue();

        int petId, petAge, adopid, emid;
        try {
            petId = Integer.parseInt(petid.getText());
            petAge = Integer.parseInt(age.getText());
            adopid = Integer.parseInt(adopterid.getText());
            emid = Integer.parseInt(employeeid.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Pet ID, age, adopter ID, and employee ID must be valid integers.");
            return;
        }

        database db = new database();
        try (Connection conn = DriverManager.getConnection(db.url, db.user, db.password)) {
            String checkAdopterSql = "SELECT COUNT(*) FROM adopter WHERE adopterid = ?";
            try (PreparedStatement checkAdopterStmt = conn.prepareStatement(checkAdopterSql)) {
                checkAdopterStmt.setInt(1, adopid);
                ResultSet rs = checkAdopterStmt.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    showAlert(Alert.AlertType.ERROR, "Adopter ID does not exist.");
                    return;
                }
            }
            String checkEmployeeSql = "SELECT COUNT(*) FROM employee WHERE ssn = ?";
            try (PreparedStatement checkEmployeeStmt = conn.prepareStatement(checkEmployeeSql)) {
                checkEmployeeStmt.setInt(1, emid);
                ResultSet rs = checkEmployeeStmt.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    showAlert(Alert.AlertType.ERROR, "Employee ID does not exist.");
                    return;
                }
            }
            String sql = "UPDATE pet SET petname = ?, age = ?, admissiondate = ?, breed = ?, species = ?, adoption_status = ?, adopter_id = ?, employee_id = ? WHERE petid = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, petName);
                pstmt.setInt(2, petAge);
                pstmt.setDate(3, java.sql.Date.valueOf(arrivalDate));
                pstmt.setString(4, breedText);
                pstmt.setString(5, speciesText);
                pstmt.setString(6, adoptionStatus);
                pstmt.setInt(7, adopid);
                pstmt.setInt(8, emid);
                pstmt.setInt(9, petId);

                pstmt.executeUpdate();
                showAlert(Alert.AlertType.INFORMATION, "Pet information updated successfully.");
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

















    public void goTomeue() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("menu.fxml"));
        searchepet.getChildren().setAll(root);
    }

    public void goTopet() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("Pet.fxml"));
        searchepet.getChildren().setAll(root);
    }
}
