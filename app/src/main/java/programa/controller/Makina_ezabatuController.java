package programa.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import programa.App;

public class Makina_ezabatuController {

    @FXML
    private void atzeraBueltatu() throws IOException {
        App.setRoot(App.getPreviousRoot());
    }

    @FXML
    private void makinaEzabatu() throws IOException {
        System.out.println("makinaEzabatu clicked");
        App.setRoot("Makinak");
    }
}