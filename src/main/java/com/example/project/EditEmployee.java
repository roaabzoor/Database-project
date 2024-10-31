package com.example.project;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.DatePicker;
import java.io.IOException;


public class EditEmployee {

    @FXML
    private AnchorPane editemployee;

    @FXML
    private JFXTextField eamil1;

    @FXML
    private JFXTextField employeeid1;

    @FXML
    private JFXTextField fname1;

    @FXML
    private DatePicker hiredate1;

    @FXML
    private JFXTextField lname1;

    @FXML
    private JFXTextField password1;
    @FXML
    private JFXTextField phonenum1;
    @FXML
    private ComboBox<String> jopposition1;
    @FXML
    private JFXTextField salary1;
    @FXML
    public void initialize() {
        jopposition1.getItems().addAll("admin", "notadmin");
    }
    public void loademployee() throws IOException, SQLException {
        // Check if employee ID field is empty
        if (employeeid1.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Employee ID must be entered.");
            return;
        }

        // Debug statement to confirm Employee ID value
        System.out.println("Employee ID entered: " + employeeid1.getText());

        int empId;
        try {
            empId = Integer.parseInt(employeeid1.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Employee ID must be a valid integer.");
            return;
        }

        // Database connection
        database db = new database();
        try (Connection conn = DriverManager.getConnection(db.url, db.user, db.password)) {
            System.out.println("Database connection successful.");

            String sql = "SELECT * FROM employee WHERE ssn = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, empId);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    // Populate fields with retrieved data
                    fname1.setText(rs.getString("fname"));
                    lname1.setText(rs.getString("lname"));
                    eamil1.setText(rs.getString("email"));
                    phonenum1.setText(String.valueOf(rs.getInt("contactnumber")));
                    password1.setText(rs.getString("epassword"));
                    hiredate1.setValue(rs.getDate("hiredate").toLocalDate());
                    salary1.setText(String.valueOf(rs.getDouble("salary")));
                    jopposition1.setValue(rs.getString("jopposition"));
                    System.out.println("Employee information loaded successfully.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Employee not found with the given ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database error: " + e.getMessage());
        }
    }

    // Save the modified employee data
    public void saveemployee() throws IOException, SQLException {
        if ( fname1.getText().isEmpty() || lname1.getText().isEmpty() ||
                eamil1.getText().isEmpty() || phonenum1.getText().isEmpty() ||
                password1.getText().isEmpty() || salary1.getText().isEmpty() ||
                hiredate1.getValue() == null) {

            showAlert(Alert.AlertType.ERROR, "All fields must be filled out.");
            return;
        }

        // Password strength validation
        String empPassword = password1.getText();
        if (!isStrongPassword(empPassword)) {
            showAlert(Alert.AlertType.ERROR, "Password must be at least 8 characters long and include letters, numbers, and symbols.");
            return;
        }

        // Retrieve field values
        String firstName =  fname1.getText();
        String lastName = lname1.getText();
        String mail = eamil1.getText();
        String phoneStr = phonenum1.getText();
        String position = jopposition1.getValue();
        LocalDate hireDate = hiredate1.getValue();
        double empSalary;

        // Validate salary
        try {
            empSalary = Double.parseDouble(salary1.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Salary must be a valid number.");
            return;
        }

        // Validate phone number
        int phone;
        try {
            phone = Integer.parseInt(phoneStr);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Contact number must be a valid integer.");
            return;
        }

        int empId;
        try {
            empId = Integer.parseInt(employeeid1.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Employee ID must be a valid integer.");
            return;
        }

        // Database connection
        database db = new database();
        try (Connection conn = DriverManager.getConnection(db.url, db.user, db.password)) {
            String sql = "UPDATE employee SET fname = ?, lname = ?, email = ?, contactnumber = ?, epassword = ?, hiredate = ?, salary = ?, jopposition = ? WHERE ssn = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, firstName);
                pstmt.setString(2, lastName);
                pstmt.setString(3, mail);
                pstmt.setInt(4, phone);
                pstmt.setString(5, empPassword);
                pstmt.setDate(6, java.sql.Date.valueOf(hireDate));
                pstmt.setDouble(7, empSalary);
                pstmt.setString(8, position);
                pstmt.setInt(9, empId);
                System.out.println("Updating Employee with ID: " + empId);
                System.out.println("First Name: " + firstName);
                System.out.println("Last Name: " + lastName);
                System.out.println("Email: " + mail);
                System.out.println("Phone: " + phone);
                System.out.println("Password: " + empPassword);
                System.out.println("Hire Date: " + hireDate);
                System.out.println("Salary: " + empSalary);
                System.out.println("Job Position: " + position);

                pstmt.executeUpdate();
                showAlert(Alert.AlertType.INFORMATION, "Employee information updated successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database error: " + e.getMessage());
        }
    }

    // Password strength checker
    private boolean isStrongPassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Za-z].*") &&
                password.matches(".*[0-9].*") &&
                password.matches(".*[!@#$%^&*()].*");
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.setTitle(alertType == Alert.AlertType.ERROR ? "Input Error" : "Success");
        alert.setHeaderText(null);
        alert.showAndWait();
    }


    public void goToLogin() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("edit_employee.fxml"));
        editemployee.getChildren().setAll(root);
    }


    public void goToaddemployee() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("add_employee.fxml"));
        editemployee.getChildren().setAll(root);
    }


}