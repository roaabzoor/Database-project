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

public class SearchVeterinarian {

    @FXML
    public AnchorPane Searchveterinarian;

    @FXML
    private TableColumn<Veterinariann,String> addresscolumn;

    @FXML
    private TableColumn<Veterinariann,String> emailcolumn;

    @FXML
    private TableColumn<Veterinariann,String> fnamecolumn;

    @FXML
    private TableColumn<Veterinariann,String> idcolumn;

    @FXML
    private TableColumn<Veterinariann,String> lnamecolumn;


    @FXML
    private TableColumn<Veterinariann,String> phonecolmun;

    @FXML
    private TableColumn<Veterinariann,String> salarycolumn;

    @FXML
    private TableColumn<Veterinariann,String> speccolumn;

    @FXML
    private TableView<Veterinariann> vettable;

    @FXML
    private JFXTextField vettext;

    @FXML
    private JFXTextField nametext;

   @FXML
    public void initialize() {
        idcolumn.setCellValueFactory(new PropertyValueFactory<>("veterinarianId"));
        fnamecolumn.setCellValueFactory(new PropertyValueFactory<>("fname"));
        lnamecolumn.setCellValueFactory(new PropertyValueFactory<>("lname"));
        emailcolumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        addresscolumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        salarycolumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        speccolumn.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        phonecolmun.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        try {
            showalltable();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    public ObservableList<Veterinariann> VeterinariannList = FXCollections.observableArrayList();
    public void showalltable() throws IOException, SQLException {
        VeterinariannList.clear();
        String sql = "SELECT veterinarianid, fname, lname, email, address, salary, phonenumber, specialization FROM veterinarian;";

        try {
            database db = new database();
            try (Connection conn = DriverManager.getConnection(db.url, db.user, db.password);
                 PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("veterinarianid");
                    String firstName = rs.getString("fname");
                    String lastName = rs.getString("lname");
                    String emailValue = rs.getString("email");
                    String address = rs.getString("address");
                    int salaryvet = rs.getInt("salary");
                    String number = rs.getString("phonenumber");
                    String spec = rs.getString("specialization");

                    Veterinariann veterinariann = new Veterinariann(id, firstName, lastName, emailValue, address, salaryvet, number, spec);
                    VeterinariannList.add(veterinariann);
                }

                vettable.setItems(VeterinariannList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Connection connect() throws SQLException {
        database b = new database();
        return DriverManager.getConnection(b.url, b.user, b.password);
    }

    public void searchveterinarian() throws IOException, SQLException {

        vettable.getItems().clear();

        String idInput = vettext.getText().trim();
        String fnameInput = nametext.getText().trim();

        String query = "SELECT * FROM veterinarian WHERE 1=1";
        if (!idInput.isEmpty()) {
            query += " AND veterinarianid = ?";
        }
        if (!fnameInput.isEmpty()) {
            query += " AND fname LIKE ?";
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
            System.out.println(stmt.toString());

            ResultSet rs = stmt.executeQuery();
            ObservableList<Veterinariann> Veterinarians = FXCollections.observableArrayList();

            while (rs.next()) {
                Veterinariann veterinariann = new Veterinariann(
                 rs.getInt("veterinarianid"),
                rs.getString("fname"),
               rs.getString("lname"),
               rs.getString("email"),
               rs.getString("address"),
                 rs.getInt("salary"),
                 rs.getString("phonenumber"),
                 rs.getString("specialization")
                );
                Veterinarians.add(veterinariann);
            }

            vettable.setItems(Veterinarians);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Message: " + e.getMessage());
        }
    }

    public void deleteveterinarian() throws IOException, SQLException {
        Veterinariann selectedVeterinariann = vettable.getSelectionModel().getSelectedItem();

        if (selectedVeterinariann == null) {
            System.out.println("No veterinarian selected.");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this veterinarian?",
                ButtonType.YES, ButtonType.NO);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            String deleteQuery = "DELETE FROM veterinarian WHERE veterinarianid = ?";

            try (Connection conn = connect();
                 PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {

                stmt.setInt(1, selectedVeterinariann.getVeterinarianId());
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION,
                            "veterinarian deleted successfully.",
                            ButtonType.OK);
                    successAlert.setTitle("Deletion Successful");
                    successAlert.setHeaderText(null);
                    successAlert.showAndWait();

                    vettable.getItems().remove(selectedVeterinariann);
                } else {
                    Alert notFoundAlert = new Alert(Alert.AlertType.ERROR,
                            "No veterinarian found with the given ID.",
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







    public void goToLogin1() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("Veterinarian.fxml"));
        Searchveterinarian.getChildren().setAll(root);
    }

    public void goTomenu() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("menu.fxml"));
        Searchveterinarian.getChildren().setAll(root);
    }
}
