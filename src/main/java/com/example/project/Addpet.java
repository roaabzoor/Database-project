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
    private ComboBox<String> breed;


    @FXML
    private TextField petname;

    @FXML
    private JFXTextField empid;

    @FXML
    private JFXTextField adoperid;

    @FXML
    private ComboBox<String> species;

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

    public void savePet() throws IOException, SQLException {
        if (petname.getText().isEmpty() || age.getText().isEmpty() ||
                breed.getValue() == null || species.getValue() == null ||
                empid.getText().isEmpty() || adoperid.getText().isEmpty() || // Changed null to isEmpty
                adoptionstatus.getValue() == null || arrivaldate.getValue() == null) {

            showAlert(AlertType.ERROR, "All fields must be filled out.");
            return;
        }
        String petName = petname.getText();
        String breedText = breed.getValue();
        String speciesText = species.getValue();
        String adoptionStatus = adoptionstatus.getValue();

        int adoprid;
        try {
            adoprid = Integer.parseInt(adoperid.getText());
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Adopter ID must be a valid integer.");
            return;
        }

        int emppid;
        try {
            emppid = Integer.parseInt(empid.getText());
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Employee ID must be a valid integer.");
            return;
        }

        int petAge;
        try {
            petAge = Integer.parseInt(age.getText());
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Age must be a valid integer.");
            return;
        }

        LocalDate arrivalDate = arrivaldate.getValue();
        if (arrivalDate == null) {
            showAlert(AlertType.ERROR, "Arrival date must be selected.");
            return;
        }
        database db = new database();
        try (Connection conn = DriverManager.getConnection(db.url, db.user, db.password)) {
            String sql = "INSERT INTO pet (petname, age, admissiondate, breed, species, adoption_status, adopter_id, employee_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, petName);
                pstmt.setInt(2, petAge);
                pstmt.setDate(3, java.sql.Date.valueOf(arrivalDate));
                pstmt.setString(4, breedText);
                pstmt.setString(5, speciesText);
                pstmt.setString(6, adoptionStatus);
                pstmt.setInt(7, adoprid);
                pstmt.setInt(8, emppid);

                pstmt.executeUpdate();
                showAlert(AlertType.INFORMATION, "Pet added successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Database error: Unable to add pet.");
        }
    }

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
