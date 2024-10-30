package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Viewpet implements Initializable {
    public BorderPane Veterinarian;
    @FXML
    public HBox cardlayout;
    public List<pets> recentlyadded;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<pets> recentlyAddedPets = recentlyadded();

        recentlyadded = new ArrayList<>(recentlyAddedPets);

        try {

            for (int i = 0; i < recentlyadded.size(); i++) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("card.fxml"));

                HBox cardBox = loader.load();
                Card card = loader.getController();
                card.setdata(recentlyadded.get(i));
                cardlayout.getChildren().add(cardBox);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private List<pets> recentlyadded() {
        List<pets> pets = new ArrayList<>();

        pets pet = new pets();
        pet.setName("Losi");
        pet.setAge(20);
        pet.setBreed("Taygor");
        pet.setImage("C:\\Users\\hp\\IdeaProjects\\Database-projectt\\src\\main\\resources\\com\\example\\project\\image\\Taygor cat.jpg");

        pets.add(pet);


        pet = new pets();
        pet.setName("Losi");
        pet.setAge(20);
        pet.setBreed("Taygor");
        pet.setImage("C:\\Users\\hp\\IdeaProjects\\Database-projectt\\src\\main\\resources\\com\\example\\project\\image\\Taygor cat.jpg");

        pets.add(pet);

        pet = new pets();
        pet.setName("Losi");
        pet.setAge(20);
        pet.setBreed("Taygor");
        pet.setImage("C:\\Users\\hp\\IdeaProjects\\Database-projectt\\src\\main\\resources\\com\\example\\project\\image\\Taygor cat.jpg");

        pets.add(pet);

        return pets;
    }
}
