package programa.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import programa.App;

public class ErabiltzaileakController {

    @FXML
    private TableView<?> erabiltzaileak;

    @FXML
    private void kenduErabiltzailea() {
        System.out.println("kenduErabiltzailea clicked");
        try {
            App.setPreviousRoot("Erabiltzaileak");
            App.setRoot("erabiltzailea_ezabatu");
        } catch (Exception e) {
            System.out.println("Errorea -Erabiltzailea ezabatu-, pantaila kargatzerakoan:");
            e.printStackTrace();
        }
    }

    @FXML
    private void informazioaAldatu() {
        try {
            App.setPreviousRoot("Erabiltzaileak");
            App.setRoot("Erabiltzailea_gehitu");
        } catch (Exception e) {
            System.out.println("Errorea -Informazioa aldatu-, pantaila kargatzerakoan:");
            e.printStackTrace();
        }
    }

    @FXML
    private void gehituErabiltzailea() {
        try {
            App.setPreviousRoot("Erabiltzaileak");
            App.setRoot("Erabiltzailea_gehitu");
        } catch (Exception e) {
            System.out.println("Errorea -Erabiltzailea gehitu-, pantaila kargatzerakoan:");
            e.printStackTrace();
        }
    }

    @FXML
    private void makinakBistaratu() throws IOException {
        App.setRoot("Makinak");
    }

    @FXML
    private void piezakBistaratu() throws IOException {
        App.setRoot("Piezak");
    }

    @FXML
    private void erabiltzaileakBistaratu() throws IOException {
        App.setRoot("Erabiltzaileak");
    }
}