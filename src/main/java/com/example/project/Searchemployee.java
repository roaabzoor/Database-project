package com.example.project;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Searchemployee {
    @FXML
    public AnchorPane searchemployee;

    @FXML
    private TableColumn<Employee, String> email;

    @FXML
    private JFXTextField emailid;

    @FXML
    private JFXTextField empid;

    @FXML
    private TableView<Employee> employeetable;

    @FXML
    private TableColumn<Employee, String> fname;

    @FXML
    private TableColumn<Employee, String> gender;

    @FXML
    private TableColumn<Employee, String> hire;

    @FXML
    private TableColumn<Employee, String> job;

    @FXML
    private TableColumn<Employee, String> lname;

    @FXML
    private TableColumn<Employee, String> pass;

    @FXML
    private TableColumn<Employee, String> phone;

    @FXML
    private TableColumn<Employee, String> salary;

    @FXML
    private TableColumn<Employee, String> ssn;

    public ObservableList<Employee> EmployeeList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        ssn.setCellValueFactory(new PropertyValueFactory<>("ssn")); // Matches getSsn()
        fname.setCellValueFactory(new PropertyValueFactory<>("fname"));
        lname.setCellValueFactory(new PropertyValueFactory<>("lname"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        pass.setCellValueFactory(new PropertyValueFactory<>("epassword")); // Matches getEpassword()
        salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        phone.setCellValueFactory(new PropertyValueFactory<>("contactnumber"));
        job.setCellValueFactory(new PropertyValueFactory<>("jobPosition")); // Change to match getter
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        hire.setCellValueFactory(new PropertyValueFactory<>("hireDate"));

        try {
            showalltable();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    public void showalltable() throws IOException, SQLException {
        EmployeeList.clear();
        String sql = "SELECT ssn, fname, lname, email, epassword, salary, contactnumber, jopposition, hiredate, gender FROM employee;";

        database db = new database();

        try {
            try (Connection conn = DriverManager.getConnection(db.url, db.user, db.password);
                 PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                    while (rs.next()) {
                        int ssnValue = rs.getInt("ssn");
                        String firstName = rs.getString("fname");
                        String lastName = rs.getString("lname");
                        String emailValue = rs.getString("email");
                        String ppassword = rs.getString("epassword");
                        int salaryValue = rs.getInt("salary");
                        String contactNumber = rs.getString("contactnumber");
                        String jobPosition = rs.getString("jopposition");
                        String genderValue = rs.getString("gender");

                        LocalDate hireDate = null;
                        String hireString = rs.getString("hiredate");
                        if (hireString != null) {
                            hireDate = LocalDate.parse(hireString, formatter);

                        Employee employee = new Employee(ssnValue, firstName, lastName, emailValue, ppassword, salaryValue, contactNumber, jobPosition, hireDate, genderValue);
                        EmployeeList.add(employee);
                    }
                }
                employeetable.setItems(EmployeeList);
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Connection connect() throws SQLException {
        database b = new database();
        return DriverManager.getConnection(b.url, b.user, b.password);
    }

    public void searchemployee() throws IOException, SQLException {

        EmployeeList.clear();

        String idInput = empid.getText().trim();
        String emailInput = emailid.getText().trim();

        StringBuilder query = new StringBuilder("SELECT * FROM employee WHERE 1=1");
        if (!idInput.isEmpty()) {
            query.append(" AND ssn = ?");
        }
        if (!emailInput.isEmpty()) {
            query.append(" AND fname = ?");
        }

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            int paramIndex = 1;
            if (!idInput.isEmpty()) {
                stmt.setInt(paramIndex++, Integer.parseInt(idInput));
            }

            if (!emailInput.isEmpty()) {
                stmt.setString(paramIndex++, emailInput);
            }

            ResultSet rs = stmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("No employees found matching the criteria.");
            } else {
                while (rs.next()) {
                    LocalDate hireDate = null;
                    java.sql.Date hireDateSql = rs.getDate("hiredate");
                    if (hireDateSql != null) {
                        hireDate = hireDateSql.toLocalDate();
                    }
                    Employee employee = new Employee(
                            rs.getInt("ssn"),
                            rs.getString("fname"),
                            rs.getString("lname"),
                            rs.getString("email"),
                            rs.getString("epassword"),
                            rs.getInt("salary"),
                            rs.getString("contactnumber"),
                            rs.getString("jopposition"),
                            hireDate,
                            rs.getString("gender")
                    );
                    EmployeeList.add(employee);
                }
            }

            employeetable.setItems(EmployeeList);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Message: " + e.getMessage());
        }

    }


    public void deleteemployee() throws IOException, SQLException {
        Employee selectedEmployee = employeetable.getSelectionModel().getSelectedItem();
        if (selectedEmployee == null) {
            System.out.println("No employee selected.");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this employee?",
                ButtonType.YES, ButtonType.NO);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            String deleteQuery = "DELETE FROM employee WHERE ssn = ?";

            try (Connection conn = connect();
                 PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {

                stmt.setInt(1, selectedEmployee.getSsn());
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION,
                            "Employee deleted successfully.",
                            ButtonType.OK);
                    successAlert.setTitle("Deletion Successful");
                    successAlert.setHeaderText(null);
                    successAlert.showAndWait();

                    employeetable.getItems().remove(employeetable);
                } else {
                    Alert notFoundAlert = new Alert(Alert.AlertType.ERROR,
                            "No employee found with the given ID.",
                            ButtonType.OK);
                    notFoundAlert.setTitle("Deletion Error");
                    notFoundAlert.setHeaderText(null);
                    notFoundAlert.showAndWait();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error during deletion: " + e.getMessage());
            }
        } else {
            System.out.println("Deletion canceled.");
        }
    }

            private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void goToLogin1() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("add_employee.fxml"));
        searchemployee.getChildren().setAll(root);
    }
    public void goTomenu() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("menu.fxml"));
        searchemployee.getChildren().setAll(root);
    }

}
