package programa.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import programa.App;

public class erabiltzailea_ezabatuController {
    
    @FXML
    private void atzeraBueltatu() throws IOException {
        App.setRoot(App.getPreviousRoot());
    }
}
