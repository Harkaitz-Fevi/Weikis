package programa.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import programa.App;
import programa.model.makina;

public class makina_gehituController {

    @FXML TextField idMakina;
    @FXML TextField idizena;
    @FXML TextField iddeskribapena;
    @FXML TextField idpotentzia;
    @FXML TextField idinstalazioa;

    @FXML
    private void makinaGehitu() throws IOException {

        String id = idMakina.getText();
        String izen = idizena.getText();
        String deskribapena = iddeskribapena.getText();
        String potentzia = idpotentzia.getText();
        String instalazioa = idinstalazioa.getText();

        makina makinaBerria = new makina(id, izen, deskribapena, potentzia, instalazioa);

        System.out.println("Makina Gehitu da: " + makinaBerria.toString());
        App.setRoot("primary");

    }
    public void atzeraBueltatu() throws IOException {
        App.setRoot(App.getPreviousRoot());
    }
}
