package programa.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import programa.App;

public class PiezaEzabatuController {

        @FXML
    private TextField txtIdEzabatu; // Fxml-en id-a duen TextField, ezabatzeko Id-a sartzeko

    // Datu Basearen konfigurazioa 
    private final String URL = "jdbc:mysql://localhost:3306/maltunaDB";
    private final String USER = "root";
    private final String PASS = "12345678";

    @FXML
    void piezaEzabatu(ActionEvent event) {
        String idTexto = txtIdEzabatu.getText().trim();

        if (idTexto.isEmpty()) {
            mostrarAlerta("Kontuz!", "Borratzeko ID bat sartu behar duzu.");
            return;
        }

        // Honek borratzen du Id_pieza balioa duten piezak
        String sql = "DELETE FROM piezak WHERE Id_pieza = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(URL, USER, PASS); 
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                int id = Integer.parseInt(idTexto);
                pstmt.setInt(1, id);

                int filasBorradas = pstmt.executeUpdate();

                if (filasBorradas > 0) {
                    mostrarAlerta("Ondo", "" + id + "Id-a duen pieza zuzen borratu da.");
                    txtIdEzabatu.clear();
                } else {
                    mostrarAlerta("Kontuz", " Ez da existitzen " + id + "-a duen pieza.");
                }
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Formatua gaizki", "ID-a zenbaki oso bat izan behar da.");
        } catch (SQLException | ClassNotFoundException e) {
            mostrarAlerta("Datu basearen errorea", e.getMessage());
        }
    }

    @FXML
    void atzeraBueltatu(ActionEvent event) throws IOException {
       
        App.setRoot(App.getPreviousRoot()); 
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}