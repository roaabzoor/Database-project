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

    public void gotoaddadopter() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("addAdopter.fxml"));
        searchadopter.getChildren().setAll(root);
    }
    public void gotomenu() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        searchadopter.getChildren().setAll(root);
    }
}
