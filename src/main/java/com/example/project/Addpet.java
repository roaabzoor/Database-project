package com.example.project;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class Addpet {

    @FXML
    public AnchorPane adddpet;

    @FXML
    private ComboBox<String> adoptionstatus;

    @FXML
    private TextField age;

    @FXML
    private DatePicker arrivaldate;

    @FXML
    private TextField breed;

    @FXML
    private TextField petname;

    @FXML
    private TextField species;

    // Initialize method to populate adoption status options
    public void initialize() {
        adoptionstatus.getItems().addAll("Adopted", "Available");
    }

    // Method to add a new pet to the database
    public void savePet() throws IOException, SQLException {
        // Validate input fields
        if (petname.getText().isEmpty() || age.getText().isEmpty() || breed.getText().isEmpty() ||
                species.getText().isEmpty() || adoptionstatus.getValue() == null || arrivaldate.getValue() == null) {

            showAlert(AlertType.ERROR, "All fields must be filled out.");
            return;
        }

        // Retrieve and validate field values
        String petName = petname.getText();
        String breedText = breed.getText();
        String speciesText = species.getText();
        String adoptionStatus = adoptionstatus.getValue();  // Use getValue for ComboBox
        int petAge;

        try {
            petAge = Integer.parseInt(age.getText());
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Age must be a valid integer.");
            return;
        }

        LocalDate arrivalDate = arrivaldate.getValue();

        // Database connection
        database db = new database();
        try (Connection conn = DriverManager.getConnection(db.url, db.user, db.password)) {
            String sql = "INSERT INTO pet (petname, age, admissiondate, breed, species, adoption_status) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, petName);
                pstmt.setInt(2, petAge);
                pstmt.setDate(3, java.sql.Date.valueOf(arrivalDate));
                pstmt.setString(4, breedText);
                pstmt.setString(5, speciesText);
                pstmt.setString(6, adoptionStatus);

                pstmt.executeUpdate();
                showAlert(AlertType.INFORMATION, "Pet added successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Database error: " + e.getMessage());
        }
    }

    // Method to show alerts for success and error messages
    private void showAlert(AlertType alertType, String message) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.setTitle(alertType == AlertType.ERROR ? "Input Error" : "Success");
        alert.setHeaderText(null);
        alert.showAndWait();
    }


    public void goToLogin1() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("Pet.fxml"));
        adddpet.getChildren().setAll(root);
    }

    public void goTomenu() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        adddpet.getChildren().setAll(root);
    }
    /*
    public void getImageFromUser(){
        try {
            FileChooser filechoser = new FileChooser();
            File f = filechoser.showOpenDialog(new Stage());
            System.out.println(f.getPath());
            Image i = new Image(f.getPath());
            tempImage.setImage(i);
        }catch(Exception e){
            System.out.println("here");
        }
    }*/
}
