package com.example.project;

import javafx.scene.image.ImageView;
public class pets {
    private String name;
    private String image; // Store image path as a String
    private String breed;
    private int age;

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for age
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // Getter and Setter for image (as a String path)
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    // Getter and Setter for breed
    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }
}
