package com.example.project;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class Adddemployee extends database{

    @FXML
    public AnchorPane adddemployee;

    @FXML
    private DatePicker hire;

    @FXML
    private JFXTextField email;

    @FXML
    private RadioButton male;

    @FXML
    private JFXTextField firstname;

    @FXML
    private JFXTextField lastname;

    @FXML
    private JFXTextField passwordemp;

    @FXML
    private ComboBox<String> joppostion;

    @FXML
    private JFXTextField phonenumber;

    @FXML
    private JFXTextField salary;

    @FXML
    public void initialize() {
        joppostion.getItems().addAll("admin", "notadmin");
    }

    public void saveemployee() throws IOException,SQLException {
        if (firstname.getText().isEmpty() || lastname.getText().isEmpty() ||
                email.getText().isEmpty() || phonenumber.getText().isEmpty() ||
                passwordemp.getText().isEmpty() || salary.getText().isEmpty()) {

            showAlert(Alert.AlertType.ERROR, "All fields must be filled out.");
            return;
        }
        String fname = firstname.getText();
        String lname = lastname.getText();
        String mail = email.getText();
        String phoneStr = phonenumber.getText();
        String password = passwordemp.getText();
        String position = joppostion.getValue();
        String gender = male.isSelected() ? "Male" : "Female";
        LocalDate hireDate = hire.getValue();
        double empSalary;
        try {
            empSalary = Double.parseDouble(salary.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Salary must be a valid number.");
            return;
        }

        int phone;
        try {
            phone = Integer.parseInt(phoneStr);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Contact number must be a valid integer.");
            return;
        }

        database db = new database();
        try (Connection conn = DriverManager.getConnection(db.url, db.user, db.password)) {
            String sql = "INSERT INTO employee (fname, lname, email, contactnumber, epassword, jopposition, gender, hiredate, salary) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, fname);
                pstmt.setString(2, lname);
                pstmt.setString(3, mail);
                pstmt.setInt(4, phone);
                pstmt.setString(5, password);
                pstmt.setString(6, position);
                pstmt.setString(7, gender);
                pstmt.setDate(8, java.sql.Date.valueOf(hireDate));
                pstmt.setDouble(9, empSalary);

                pstmt.executeUpdate();
                showAlert(Alert.AlertType.INFORMATION, "Employee added successfully.");
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
        AnchorPane root= FXMLLoader.load(getClass().getResource("add_employee.fxml"));
        adddemployee.getChildren().setAll(root);
    }

    public void goTomenu() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("menu.fxml"));
        adddemployee.getChildren().setAll(root);
    }

}
