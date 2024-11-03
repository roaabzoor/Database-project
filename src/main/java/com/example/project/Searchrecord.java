package com.example.project;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
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
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;


import javax.swing.*;
import java.io.*;
import java.sql.*;
import java.util.Optional;

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

    private Connection connect() throws SQLException {
        database b = new database();
        return DriverManager.getConnection(b.url, b.user, b.password);
    }

    public void showreports() throws IOException, SQLException,JRException{
        try (Connection conn = connect();
             InputStream inp = new FileInputStream(new File("record - Copy.jrxml"))){

             JasperDesign jd = JRXmlLoader.load(inp);
             JasperReport jr = JasperCompileManager.compileReport(jd);
             JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);

             JFrame frame = new JFrame("Report");
             frame.getContentPane().add(new JRViewer(jp));
             frame.setSize(600, 600);
             frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             frame.setVisible(true);
            conn.close();
            inp.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, "Report generation error: " + e.getMessage());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File error: " + e.getMessage());
        }
    }


    @FXML
    public void initialize() {
        idcolumn.setCellValueFactory(new PropertyValueFactory<>("recordId"));
        diagnosiscolumn.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));
        treatmentcolumn.setCellValueFactory(new PropertyValueFactory<>("treatment"));
        vetcolmn.setCellValueFactory(new PropertyValueFactory<>("veterinarianId"));
        petcolumn.setCellValueFactory(new PropertyValueFactory<>("petId"));
        dosacolumn.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        stautscolmn.setCellValueFactory(new PropertyValueFactory<>("vaccinationStatus"));

        try {
            showalltable();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void showalltable() throws IOException, SQLException {
        recordList.clear();

        String sql = "SELECT * FROM medicalrecord";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (!rs.isBeforeFirst()) {
                System.out.println("No records found.");
            } else {
                while (rs.next()) {
                    Record record = new Record(
                            rs.getInt("recordid"),
                            rs.getString("diagnosis"),
                            rs.getString("treatment"),
                            rs.getString("dosage"),
                            rs.getString("vaccination_status"),
                            rs.getInt("pet_id"),
                            rs.getInt("veterinarian_id")
                    );
                    recordList.add(record); // Add to the observable list
                }
                Platform.runLater(() -> recordtable.setItems(recordList));
            }

        } catch (SQLException e) {
            System.out.println("SQL Error Code: " + e.getErrorCode());
            System.out.println("SQL State: " + e.getSQLState());
            e.printStackTrace();
        }
    }

    public void searchrecord() throws IOException, SQLException {
        recordtable.getItems().clear();

        String idInput = medicaltext.getText().trim();
        String vetInput = vettext.getText().trim();
        String petInput = petext.getText().trim();

        String query = "SELECT * FROM medicalrecord WHERE 1=1";
        if (!idInput.isEmpty()) {
            query += " AND recordid = ?";
        }
        if (!vetInput.isEmpty()) {
            query += " AND veterinarian_id = ?";
        }
        if (!petInput.isEmpty()) {
            query += " AND pet_id = ?";
        }

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            int paramIndex = 1;
            if (!idInput.isEmpty()) {
                stmt.setInt(paramIndex++, Integer.parseInt(idInput));
            }
            if (!vetInput.isEmpty()) {
                stmt.setInt(paramIndex++, Integer.parseInt(vetInput));
            }
            if (!petInput.isEmpty()) {
                stmt.setInt(paramIndex++, Integer.parseInt(petInput));
            }

            System.out.println(stmt.toString());

            ResultSet rs = stmt.executeQuery();
            ObservableList<Record> Records = FXCollections.observableArrayList();

            while (rs.next()) {
                Record record = new Record(
                        rs.getInt("recordid"),
                        rs.getString("diagnosis"),
                        rs.getString("treatment"),
                        rs.getString("dosage"),
                        rs.getString("vaccination_status"),
                        rs.getInt("pet_id"),
                        rs.getInt("veterinarian_id")
                );
                Records.add(record);
            }
            recordtable.setItems(Records);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Message: " + e.getMessage());
        }
    }

    public void deleterecord() throws IOException, SQLException {
        Record selectedrecord = recordtable.getSelectionModel().getSelectedItem();

        if (selectedrecord == null) {
            JOptionPane.showMessageDialog(null, "No record selected.", "Alert", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this Record?",
                ButtonType.YES, ButtonType.NO);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            String deleteQuery = "DELETE FROM medicalrecord WHERE recordid = ?";

            try (Connection conn = connect();
                 PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {

                stmt.setInt(1, selectedrecord.getRecordId());
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION,
                            "Record deleted successfully.",
                            ButtonType.OK);
                    successAlert.setTitle("Deletion Successful");
                    successAlert.setHeaderText(null);
                    successAlert.showAndWait();

                    recordtable.getItems().remove(selectedrecord);
                } else {
                    Alert notFoundAlert = new Alert(Alert.AlertType.ERROR,
                            "No record found with the given ID.",
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
    public void goTomenu() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("menu.fxml"));
        searchrecord.getChildren().setAll(root);
    }
    public void goTomidical() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("midicalrecord.fxml"));
        searchrecord.getChildren().setAll(root);
    }
}
