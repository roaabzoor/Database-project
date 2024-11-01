package com.example.project;

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

public class Editadopter {

    @FXML
    private TableView<Adopter> adoptertable;

    @FXML
    private TableColumn<Adopter, String> addresscoulmn;

    @FXML
    private JFXTextField adopterid;

    @FXML
    private TableColumn<Adopter, String> agecolumn;

    @FXML
    private JFXTextField email;

    @FXML
    private TableColumn<Adopter, String> emailcoulmn;

    @FXML
    private JFXTextField fname;

    @FXML
    private TableColumn<Adopter,String> fnamecolumn;

    @FXML
    private TableColumn<Adopter, String> id;

    @FXML
    private TableColumn<Adopter, String> lnamecolmn;

    @FXML
    private TableColumn<Adopter, String> number;

    @FXML
    public void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<>("adopterid"));
        fnamecolumn.setCellValueFactory(new PropertyValueFactory<>("fname"));
        lnamecolmn.setCellValueFactory(new PropertyValueFactory<>("lname"));
        emailcoulmn.setCellValueFactory(new PropertyValueFactory<>("email"));
        addresscoulmn.setCellValueFactory(new PropertyValueFactory<>("address"));
        agecolumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        number.setCellValueFactory(new PropertyValueFactory<>("contactnumber"));
        try {
            showalltable();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection connect() throws SQLException {
        database b = new database();
        return DriverManager.getConnection(b.url, b.user, b.password);
    }
    public AnchorPane searchadopter ;
    public ObservableList<Adopter> adopterList = FXCollections.observableArrayList();
    public void showalltable() throws IOException, SQLException {
        adopterList.clear();
        String sql = "SELECT adopterid, fname, lname, email, address, age, contactnumber FROM adopter;";

        try {
            database db = new database();
            try (Connection conn = DriverManager.getConnection(db.url, db.user, db.password);
                 PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("adopterid");
                    String firstName = rs.getString("fname");
                    String lastName = rs.getString("lname");
                    String emailValue = rs.getString("email");
                    String address = rs.getString("address");
                    int age = rs.getInt("age");
                    String number = rs.getString("contactnumber");

                    Adopter adopter = new Adopter(id, firstName, lastName, emailValue, address, age, number);
                    adopterList.add(adopter);
                }

                adoptertable.setItems(adopterList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void searchadopter() throws IOException, SQLException {

        adoptertable.getItems().clear();

        String idInput = adopterid.getText().trim();
        String fnameInput = fname.getText().trim();
        String emailInput = email.getText().trim();

        String query = "SELECT * FROM adopter WHERE 1=1";
        if (!idInput.isEmpty()) {
            query += " AND adopterid = ?";
        }
        if (!fnameInput.isEmpty()) {
            query += " AND fname LIKE ?";
        }
        if (!emailInput.isEmpty()) {
            query += " AND email = ?";
        }

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            int paramIndex = 1;
            if (!idInput.isEmpty()) {
                stmt.setInt(paramIndex++, Integer.parseInt(idInput));
            }
            if (!fnameInput.isEmpty()) {
                stmt.setString(paramIndex++, "%" + fnameInput + "%");
            }
            if (!emailInput.isEmpty()) {
                stmt.setString(paramIndex++, emailInput);
            }

            System.out.println(stmt.toString());

            ResultSet rs = stmt.executeQuery();
            ObservableList<Adopter> adopters = FXCollections.observableArrayList();

            while (rs.next()) {
                Adopter adopter = new Adopter(
                        rs.getInt("adopterid"),
                        rs.getString("fname"),
                        rs.getString("lname"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getInt("age"),
                        rs.getString("contactnumber")
                );
                adopters.add(adopter);
            }

            adoptertable.setItems(adopters);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Message: " + e.getMessage());
        }
    }

    public void deletedopter() throws IOException, SQLException {
        Adopter selectedAdopter = adoptertable.getSelectionModel().getSelectedItem();

        if (selectedAdopter == null) {
            System.out.println("No adopter selected.");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this adopter?",
                ButtonType.YES, ButtonType.NO);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            String deleteQuery = "DELETE FROM adopter WHERE adopterid = ?";

            try (Connection conn = connect();
                 PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {

                stmt.setInt(1, selectedAdopter.getAdopterid());
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION,
                            "Adopter deleted successfully.",
                            ButtonType.OK);
                    successAlert.setTitle("Deletion Successful");
                    successAlert.setHeaderText(null);
                    successAlert.showAndWait();

                    adoptertable.getItems().remove(selectedAdopter);
                } else {
                    Alert notFoundAlert = new Alert(Alert.AlertType.ERROR,
                            "No adopter found with the given ID.",
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

    public void gotoaddadopter() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("addAdopter.fxml"));
        searchadopter.getChildren().setAll(root);
    }
    public void gotomenu() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        searchadopter.getChildren().setAll(root);
    }
}
