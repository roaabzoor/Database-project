package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import com.jfoenix.controls.JFXTextField;


import java.io.IOException;
import java.sql.*;
public class Editsupply {
    @FXML
    public AnchorPane editsupply;

    @FXML
    private JFXTextField quantitty;

    @FXML
    private JFXTextField supplierid;

    @FXML
    private JFXTextField supplyid;

    @FXML
    private JFXTextField supplyname;

    @FXML
    private JFXTextField totalcost;
    // Method to load supply record from the database
    public void loadSupplyRecord() throws IOException {
        if (supplyid.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Supply ID must be entered.");
            return;
        }

        int supplyId;
        try {
            supplyId = Integer.parseInt(supplyid.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Supply ID must be a valid integer.");
            return;
        }

        // Database connection
        database db = new database();
        try (Connection conn = DriverManager.getConnection(db.url, db.user, db.password)) {
            String sql = "SELECT * FROM supplies WHERE supplyid = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, supplyId);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    // Populate fields with retrieved data
                    supplyname.setText(rs.getString("supplyname"));
                    quantitty.setText(rs.getString("quantity"));
                    totalcost.setText(rs.getString("total_cost"));
                    supplierid.setText(rs.getString("employeeid")); // Make sure to use consistent naming
                } else {
                    showAlert(Alert.AlertType.ERROR, "Supply record not found with the given ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database error: " + e.getMessage());
        }
    }

    public void saveSupplyRecord() {
        if (supplyname.getText().isEmpty() || quantitty.getText().isEmpty() || totalcost.getText().isEmpty() || supplierid.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "All fields must be filled out.");
            return;
        }

        // Retrieve and parse field values
        String supplyName = supplyname.getText();
        String quantityText = quantitty.getText();

        int supplyId;
        int totalCost;
        int supplierId;  // Parse supplierid as an integer for employeeid field

        try {
            supplyId = Integer.parseInt(supplyid.getText());
            totalCost = Integer.parseInt(totalcost.getText());
            supplierId = Integer.parseInt(supplierid.getText());  // Parse as integer
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Supply ID, Total Cost, and Employee ID must be valid integers.");
            return;
        }

        // Database connection
        database db = new database();
        try (Connection conn = DriverManager.getConnection(db.url, db.user, db.password)) {
            String sql = "UPDATE supplies SET supplyname = ?, quantity = ?, total_cost = ?, employeeid = ? WHERE supplyid = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, supplyName);
                pstmt.setString(2, quantityText);
                pstmt.setInt(3, totalCost);
                pstmt.setInt(4, supplierId);  // Set employeeid as an integer
                pstmt.setInt(5, supplyId);

                pstmt.executeUpdate();
                showAlert(Alert.AlertType.INFORMATION, "Supply record information updated successfully.");
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


public void goTomenu() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("menu.fxml"));
        editsupply.getChildren().setAll(root);
    }
    public void goTosupply() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("Supplies.fxml"));
        editsupply.getChildren().setAll(root);
    }
}
