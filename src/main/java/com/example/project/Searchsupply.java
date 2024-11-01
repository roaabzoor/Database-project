package com.example.project;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class Searchsupply {
    public AnchorPane searchsupply;
    @FXML
    private TableColumn<Supply, String> quantity;

    @FXML
    private JFXTextField subid;

    @FXML
    private JFXTextField suppid;

    @FXML
    private TableColumn<Supply, String> supplierid;
    @FXML
    private TableColumn<Supply, String> supplyid;

    @FXML
    private TableColumn<Supply, String> supplyname;

    @FXML
    private TableView<Supply> supplytable;

    @FXML
    private TableColumn<Supply, String> totalcost;

    public ObservableList<Supply> supplyList = FXCollections.observableArrayList();

    private Connection connect() throws SQLException {
        database b = new database();
        Connection conn = DriverManager.getConnection(b.url, b.user, b.password);
        System.out.println("Connected to the database: " + b.url);
        return conn;
    }
    @FXML
    public void initialize() {
        supplyid.setCellValueFactory(new PropertyValueFactory<>("supplyid"));
        supplyname.setCellValueFactory(new PropertyValueFactory<>("supplyname"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        totalcost.setCellValueFactory(new PropertyValueFactory<>("total_cost"));
        supplierid.setCellValueFactory(new PropertyValueFactory<>("employeeid"));

        try {
            showalltable();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchsupply() throws IOException, SQLException {
        supplytable.getItems().clear();

        String idInput = subid.getText().trim();
        String employeeIdInput = suppid.getText().trim();

        String query = "SELECT * FROM supplies WHERE 1=1";
        if (!idInput.isEmpty()) {
            query += " AND supplyid = ?";
        }
        if (!employeeIdInput.isEmpty()) {
            query += " AND employeeid = ?";
        }

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            int paramIndex = 1;
            if (!idInput.isEmpty()) {
                try {
                    stmt.setInt(paramIndex++, Integer.parseInt(idInput));
                } catch (NumberFormatException e) {
                    showAlert("Invalid Input", "Supply ID must be a valid integer: '" + idInput + "'");                    return;
                }
            }

            if (!employeeIdInput.isEmpty()) {
                try {
                    stmt.setInt(paramIndex++, Integer.parseInt(employeeIdInput));
                } catch (NumberFormatException e) {
                    showAlert("Invalid Input", "Employee ID must be a valid integer: '" + employeeIdInput + "'");
                    return;
                }
            }
            System.out.println(stmt.toString());
            ResultSet rs = stmt.executeQuery();
            ObservableList<Supply> supplies = FXCollections.observableArrayList();

            while (rs.next()) {
                Supply supply = new Supply(
                        rs.getInt("supplyid"),
                        rs.getString("supplyname"),
                        rs.getInt("quantity"),
                        rs.getInt("total_cost"),
                        rs.getInt("employeeid")
                );
                supplies.add(supply);
            }

            supplytable.setItems(supplies);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Message: " + e.getMessage());
        }
    }
        private void showAlert(String title, String message) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);

            alert.showAndWait();

    }

    public void showalltable() throws IOException , SQLException{
            supplyList.clear();
            String sql = "SELECT supplyid, supplyname, quantity, total_cost, employeeid FROM supplies;";

            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    Supply supply = new Supply(
                            rs.getInt("supplyid"),
                            rs.getString("supplyname"),
                            rs.getInt("quantity"),
                            rs.getInt("total_cost"),
                            rs.getInt("employeeid")
                    );
                    supplyList.add(supply);
                }

                supplytable.setItems(supplyList);
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public void deletesupply() throws IOException, SQLException {
        Supply selectedAdopter = supplytable.getSelectionModel().getSelectedItem();

        if (selectedAdopter == null) {
            System.out.println("No supply selected.");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this supply?",
                ButtonType.YES, ButtonType.NO);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            String deleteQuery = "DELETE FROM supplies WHERE supplyid = ?";

            try (Connection conn = connect();
                 PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {

                stmt.setInt(1, selectedAdopter.getsupplyid());
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION,
                            "Supply deleted successfully.",
                            ButtonType.OK);
                    successAlert.setTitle("Deletion Successful");
                    successAlert.setHeaderText(null);
                    successAlert.showAndWait();

                    supplytable.getItems().remove(selectedAdopter);
                } else {
                    Alert notFoundAlert = new Alert(Alert.AlertType.ERROR,
                            "No Supply found with the given ID.",
                            ButtonType.OK);
                    notFoundAlert.setTitle("Deletion Error");
                    notFoundAlert.setHeaderText(null);
                    notFoundAlert.showAndWait();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Deletion canceled.");
        }
    }

    public void goTomenu() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("Supplies.fxml"));
        searchsupply.getChildren().setAll(root);
    }
}
