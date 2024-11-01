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
public class Editpet {

    @FXML
    private TableView<pettt> pettable;

    @FXML
    private TableColumn<pettt, String> petidcoulmn;

    @FXML
    private TableColumn<pettt, String> petnamecolumn;



    @FXML
    private TableColumn<pettt, String> agecoulmn;


    @FXML
    private TableColumn<pettt, String> breedcoulmn;
    @FXML
    private TableColumn<pettt, String> speciescolumn;

    @FXML
    private TableColumn<pettt, String> adoptiondatecolumn;


    @FXML
    private TableColumn<pettt, String> admissiondatecolmn;

    @FXML
    private TableColumn<pettt, String> adopteridcolumn;

    @FXML
    private TableColumn<pettt, String> employeeidcolumn;

    @FXML
    private JFXTextField petiid;
    @FXML
    private JFXTextField adoptid;

    @FXML
    public void initialize() {
        // Set cell value factories for the pet table columns
        petidcoulmn.setCellValueFactory(new PropertyValueFactory<>("petid"));
        petnamecolumn.setCellValueFactory(new PropertyValueFactory<>("petname"));
        agecoulmn.setCellValueFactory(new PropertyValueFactory<>("age"));
        breedcoulmn.setCellValueFactory(new PropertyValueFactory<>("breed"));
        speciescolumn.setCellValueFactory(new PropertyValueFactory<>("species"));
        adoptiondatecolumn.setCellValueFactory(new PropertyValueFactory<>("adoptionDate"));
        admissiondatecolmn.setCellValueFactory(new PropertyValueFactory<>("admissionDate"));
        adopteridcolumn.setCellValueFactory(new PropertyValueFactory<>("adopterid"));
        employeeidcolumn.setCellValueFactory(new PropertyValueFactory<>("employeeid"));

        try {
            showAllPets();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection connect() throws SQLException {
        database db = new database();
        return DriverManager.getConnection(db.url, db.user, db.password);
    }

    public ObservableList<pettt> petList = FXCollections.observableArrayList();

    public void showAllPets() throws IOException, SQLException {
        petList.clear();
        String sql = "SELECT petid, petname, age, breed, species, adoption_status, admissiondate, adopter_id, employee_id FROM pet;";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int petid = rs.getInt("petid");
                String petName = rs.getString("petname");
                int age = rs.getInt("age");
                String breed = rs.getString("breed");
                String species = rs.getString("species");
                String adoptionDate = rs.getString("admissiondate");
                String admissionDate = rs.getString("adoption_status");
                int adopterId = rs.getInt("adopter_id");
                int employeeId = rs.getInt("employee_id");

                pettt pet = new pettt(petid, petName, age, breed, species, adoptionDate, admissionDate, adopterId, employeeId);
                petList.add(pet);
            }

            pettable.setItems(petList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void searchPet() throws IOException, SQLException {
        pettable.getItems().clear();

        String petIdInput =  petiid.getText().trim();
        String adoptIdInput = adoptid.getText().trim();

        String query = "SELECT * FROM pet WHERE 1=1";
        if (!petIdInput.isEmpty()) {
            query += " AND petid = ?";
        }
        if (!adoptIdInput.isEmpty()) {
            query += " AND adopter_id = ?";
        }

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            int paramIndex = 1;
            if (!petIdInput.isEmpty()) {
                stmt.setInt(paramIndex++, Integer.parseInt(petIdInput));
            }
            if (!adoptIdInput.isEmpty()) {
                stmt.setInt(paramIndex++, Integer.parseInt(adoptIdInput));
            }

            ResultSet rs = stmt.executeQuery();
            ObservableList<pettt> pets = FXCollections.observableArrayList();

            while (rs.next()) {
                pettt pet = new pettt(
                        rs.getInt("petid"),
                        rs.getString("petname"),
                        rs.getInt("age"),
                        rs.getString("breed"),
                        rs.getString("species"),
                        rs.getString("adoption_status"),  // corrected column
                        rs.getString("admissiondate"),    // corrected column
                        rs.getInt("adopter_id"),
                        rs.getInt("employee_id")
                );
                pets.add(pet);
            }

            pettable.setItems(pets);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Message: " + e.getMessage());
        }
    }

    public void deletePet() throws IOException, SQLException {
        pettt selectedPet = pettable.getSelectionModel().getSelectedItem();

        if (selectedPet == null) {
            System.out.println("No pet selected.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this pet?",
                ButtonType.YES, ButtonType.NO);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            String deleteQuery = "DELETE FROM pet WHERE petid = ?";

            try (Connection conn = connect();
                 PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {

                stmt.setInt(1, selectedPet.getPetid());
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION,
                            "Pet deleted successfully.",
                            ButtonType.OK);
                    successAlert.setTitle("Deletion Successful");
                    successAlert.setHeaderText(null);
                    successAlert.showAndWait();

                    pettable.getItems().remove(selectedPet);
                } else {
                    Alert notFoundAlert = new Alert(Alert.AlertType.ERROR,
                            "No pet found with the given ID.",
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




    @FXML
    public AnchorPane editepet;
    public void goTomeue() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("menu.fxml"));
        editepet.getChildren().setAll(root);
    }

    public void goTopet() throws IOException {
        AnchorPane root= FXMLLoader.load(getClass().getResource("Pet.fxml"));
        editepet.getChildren().setAll(root);
    }
}