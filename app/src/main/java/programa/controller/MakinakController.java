package programa.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import programa.App;

public class MakinakController {

    @FXML
    private TableView<?> erabiltzaileak;

    @FXML
    private void makina_kendu() {
        System.out.println("makina_kendu clicked");
        try {
            App.setPreviousRoot("Makinak");
            App.setRoot("Makina_ezabatu");
        } catch (Exception e) {
            System.out.println("Error navigating to Makina_ezabatu:");
            e.printStackTrace();
        }
    }

    @FXML
    private void informazioa_aldatu() {
        try {
            App.setPreviousRoot("Makinak");
            App.setRoot("Makina_gehitu");
        } catch (Exception e) {
            System.out.println("Error navigating to Makina_gehitu:");
            e.printStackTrace();
        }
    }

    @FXML
    private void makina_gehitu() {
        try {
            App.setPreviousRoot("Makinak");
            App.setRoot("Makina_gehitu");
        } catch (Exception e) {
            System.out.println("Error navigating to Makina_gehitu:");
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