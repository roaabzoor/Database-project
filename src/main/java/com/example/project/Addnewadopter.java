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

public class Addnewadopter {
    @FXML
    public AnchorPane addnew ;

    @FXML
    private Label invalid;

    @FXML
    private JFXTextField address;

    @FXML
    private JFXTextField age;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField firstname;

    @FXML
    private JFXTextField lastname;

    @FXML
    private JFXTextField phonenumber;

    public void saveemployee() throws IOException, SQLException {
        if (firstname.getText().isEmpty() || lastname.getText().isEmpty() ||
                email.getText().isEmpty() || phonenumber.getText().isEmpty() ||
                address.getText().isEmpty() || age.getText().isEmpty()) {

            showAlert(Alert.AlertType.ERROR, "All fields must be filled out.");
            return;
        }

        String fname = firstname.getText();
        String lname = lastname.getText();
        String mail = email.getText();
        String phoneStr = phonenumber.getText();
        String addresss = address.getText();
        String agee = age.getText();

        int phone;
        if (phoneStr.isEmpty() || !phoneStr.matches("\\d+")) {
            invalid.setText("Invalid input.");
            invalid.setVisible(true);
            return;
        } else {
            invalid.setVisible(false);
            try {
                phone = Integer.parseInt(phoneStr);
            } catch (NumberFormatException e) {
                invalid.setText("Invalid input.");
                invalid.setVisible(true);
                return;
            }
        }
        int aage;
        try {
            aage = Integer.parseInt(agee);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Age must be a valid integer.");
            return;
        }

        database db = new database();
        try (Connection conn = DriverManager.getConnection(db.url, db.user, db.password)) {
            String sql = "INSERT INTO adopter (fname, lname, email, contactnumber, address, age) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, fname);
                pstmt.setString(2, lname);
                pstmt.setString(3, mail);
                pstmt.setInt(4, phone);
                pstmt.setString(5, addresss);
                pstmt.setInt(6, aage);

                pstmt.executeUpdate();
                showAlert(Alert.AlertType.INFORMATION, "Adopter added successfully.");
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

    public void gotomenu() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        addnew.getChildren().setAll(root);
    }
    public void gotoaddadopter() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("addAdopter.fxml"));
        addnew.getChildren().setAll(root);
    }


}
