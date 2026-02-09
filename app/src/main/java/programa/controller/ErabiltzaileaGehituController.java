package programa.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import programa.App;

public class ErabiltzaileaGehituController {

    @FXML
    private void atzeraBueltatu() throws IOException {
        App.setRoot(App.getPreviousRoot());
    }

    @FXML
    private void gehitu() throws IOException {
        System.out.println("gehitu Erabiltzailea clicked");
        App.setRoot("Erabiltzaileak");
    }
}