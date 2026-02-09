package programa.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class PrincipalBienController {

    @FXML
    private ImageView avatarView;

    @FXML
    private ImageView mainImageView;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private Label pageTitleLabel;

    @FXML
    private void initialize() {
        try {
            avatarView.setImage(new Image("file:/C:/Users/web25hferreira/Documents/Weikis/2526_2erronka_JS-main/assets/img/favicon.png"));
            mainImageView.setImage(new Image("file:/C:/Users/web25hferreira/Documents/Weikis/2526_2erronka_JS-main/assets/img/maltuna.jpg"));
            System.out.println("[UI] PrincipalBien initialized - images set");
        } catch (Exception e) {
            System.out.println("[UI] Error setting images:");
            e.printStackTrace();
        }
    }

    @FXML
    private void makinakBistaratu() {
        System.out.println("[UI] makinakBistaratu clicked");
        try {
            Parent center = FXMLLoader.load(getClass().getResource("/programa/fragments/MakinakCenter.fxml"));
            mainBorderPane.setCenter(center);
            pageTitleLabel.setText("Makinak");
        } catch (IOException e) {
            System.out.println("Error loading MakinakCenter.fxml:");
            e.printStackTrace();
        }
    }

    @FXML
    private void piezakBistaratu() {
        System.out.println("[UI] piezakBistaratu clicked");
        try {
            Parent center = FXMLLoader.load(getClass().getResource("/programa/fragments/PiezakCenter.fxml"));
            mainBorderPane.setCenter(center);
            pageTitleLabel.setText("Piezak");
        } catch (IOException e) {
            System.out.println("Error loading PiezakCenter.fxml:");
            e.printStackTrace();
        }
    }

    @FXML
    private void erabiltzaileakBistaratu() {
        System.out.println("[UI] erabiltzaileakBistaratu clicked");
        try {
            Parent center = FXMLLoader.load(getClass().getResource("/programa/fragments/ErabiltzaileakCenter.fxml"));
            mainBorderPane.setCenter(center);
            pageTitleLabel.setText("Erabiltzaileak");
        } catch (IOException e) {
            System.out.println("Error loading ErabiltzaileakCenter.fxml:");
            e.printStackTrace();
        }
    }
}