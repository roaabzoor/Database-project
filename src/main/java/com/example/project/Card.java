package com.example.project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;



public class Card {

    @FXML
    private HBox box;

    @FXML
    private Label catage;

    @FXML
    private Label catbreed;

    @FXML
    private Label catname;




    public void setdata(pets pet) {



        catname.setText(pet.getName());
        catbreed.setText(pet.getBreed());
        catage.setText(String.valueOf(pet.getAge()));
    }

}
