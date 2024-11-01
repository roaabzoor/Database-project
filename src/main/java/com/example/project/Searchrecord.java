package com.example.project;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.*;

public class Searchrecord {
    @FXML
    public AnchorPane searchrecord;

    @FXML
    private TableColumn<Record,String> diagnosiscolumn;

    @FXML
    private TableColumn<Record,String> dosacolumn;

    @FXML
    private TableColumn<Record,String> idcolumn;

    @FXML
    private JFXTextField medicaltext;

    @FXML
    private JFXTextField vettext;

    @FXML
    private TableColumn<Record,String> petcolumn;

    @FXML
    private JFXTextField petext;

    @FXML
    private TableView<Record> recordtable;
    @FXML
    private TableColumn<Record,String> stautscolmn;

    @FXML
    private TableColumn<Record,String> treatmentcolumn;

    @FXML
    private TableColumn<Record,String> vetcolmn;

    public ObservableList<Record> recordList = FXCollections.observableArrayList();
    @FXML
    /*public void initialize() {
        // Set up cell value factories for each column
        idcolumn.setCellValueFactory(new PropertyValueFactory<>("recordId"));
        diagnosiscolumn.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));
        treatmentcolumn.setCellValueFactory(new PropertyValueFactory<>("treatment"));
        vetcolmn.setCellValueFactory(new PropertyValueFactory<>("veterinarianId"));
        petcolumn.setCellValueFactory(new PropertyValueFactory<>("petId"));
        dosacolumn.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        stautscolmn.setCellValueFactory(new PropertyValueFactory<>("vaccinationStatus"));

        // Load data into the TableView
        try {
            showalltable();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }*/

    public void showalltable() throws IOException, SQLException {
        recordList.clear();
        String sql = "SELECT recordid, diagnosis, treatment, dosage, vaccination_status, pet_id, veterinarian_id FROM medicalrecord;";

        try (Connection conn = DriverManager.getConnection("your_database_url", "your_username", "your_password");
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("recordid");
                String diagnosis = rs.getString("diagnosis");
                String treatment = rs.getString("treatment");
                String vaccinationStatus = rs.getString("vaccination_status");
                int petId = rs.getInt("pet_id");
                int veterinarianId = rs.getInt("veterinarian_id");
                String dosage = rs.getString("dosage");

                // Create a new Record object and add it to the list
                Record record = new Record(id, diagnosis, treatment, dosage, vaccinationStatus, petId, veterinarianId);
                recordList.add(record);
            }

            recordtable.setItems(recordList); // Set the items for the TableView
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void goTomenu() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("menu.fxml"));
        searchrecord.getChildren().setAll(root);
    }
    public void goTomidical() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("midicalrecord.fxml"));
        searchrecord.getChildren().setAll(root);
    }
}
