package com.example.project;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.*;

public class Addsupply {
    @FXML
    public AnchorPane addsupplies;
    @FXML
    private JFXTextField quantity;

    @FXML
    private JFXTextField supplierid;

    @FXML
    private JFXTextField supplyname;

    @FXML
    private JFXTextField total;

    public void savesupply() throws IOException, SQLException {

        if (supplyname.getText().isEmpty() || supplierid.getText().isEmpty() ||
                total.getText().isEmpty() || quantity.getText().isEmpty()) {

            showAlert(Alert.AlertType.ERROR, "All fields must be filled out.");
            return;
        }

        String supname = supplyname.getText();
        String suplierid = supplierid.getText();
        String cost = total.getText();
        String quant = quantity.getText();

        int cost1;
        try {
            cost1 = Integer.parseInt(cost);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Total cost must be a valid integer.");
            return;
        }
        int quantt;
        try {
            quantt = Integer.parseInt(quant);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Quantity must be a valid integer.");
            return;
        }
        int empid;
        try {
            empid = Integer.parseInt(suplierid);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Supplier ID must be a valid integer.");
            return;
        }

        database db = new database();
        try (Connection conn = DriverManager.getConnection(db.url, db.user, db.password)) {
            String sql = "INSERT INTO supplies (supplyname, quantity, total_cost, employeeid) VALUES (?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, supname);
                pstmt.setInt(2, quantt);
                pstmt.setInt(3, cost1);
                pstmt.setInt(4, empid);

                pstmt.executeUpdate();
                String empName = getEmployeeName(conn, empid);
                showAlert(Alert.AlertType.INFORMATION, "Supply added successfully for Supplier: " + empName);
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
    public String getEmployeeName(Connection conn, int empid) {
        String empname = null;
        String query = "SELECT fname FROM employee WHERE ssn = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, empid);
            System.out.println("Executing query for Employee ID: " + empid);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                empname = rs.getString("fname");
                System.out.println("Retrieved employee name: " + empname);
            } else {
                System.out.println("No employee found with ID: " + empid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empname != null && !empname.isEmpty() ? empname : "Unknown Employee";
    }

    public void goTosupply() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("Supplies.fxml"));
        addsupplies.getChildren().setAll(root);
    }
}
