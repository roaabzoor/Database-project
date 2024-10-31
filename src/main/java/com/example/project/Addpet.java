package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;


public class Addpet {
    @FXML
    public AnchorPane adddpet;
    @FXML
    public ImageView tempImage;
    public void goToLogin1() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("Pet.fxml"));
        adddpet.getChildren().setAll(root);
    }

    public void goTomenu() throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        adddpet.getChildren().setAll(root);
    }
/*
    public void uploadImage() {
        String insertImageSql = "INSERT INTO images (name, image_data) VALUES (?, ?)";

        // Use try-with-resources to ensure all resources are closed properly
        try (database db = new database(); // Ensure this class is defined and has url, user, password
             Connection conn = DriverManager.getConnection(db.url, db.user, db.password);
             PreparedStatement pstmt = conn.prepareStatement(insertImageSql)) {

            // Read image file
            File image = new File("path/to/your/image.jpg"); // Specify the path to your image file
            try (FileInputStream fis = new FileInputStream(image)) {
                pstmt.setString(1, "example_image");
                pstmt.setBinaryStream(2, fis, (int) image.length());
                pstmt.executeUpdate();

                System.out.println("Image stored successfully!");
            } catch (IOException e) {
                System.err.println("Error reading image file: " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    public void getImageFromUser(){
        try {
            FileChooser filechoser = new FileChooser();
            File f = filechoser.showOpenDialog(new Stage());
            System.out.println(f.getPath());
            Image i = new Image(f.getPath());
            tempImage.setImage(i);
        }catch(Exception e){
            System.out.println("here");
        }
        }
}
