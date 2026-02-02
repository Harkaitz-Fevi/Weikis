package programa.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import programa.App;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
