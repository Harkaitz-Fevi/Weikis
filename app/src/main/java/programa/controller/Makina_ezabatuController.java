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

public class Makina_ezabatuController {

    @FXML
    private TextField txtIdEzabatu; // Fxml-en id-a duen TextField, ezabatzeko Id-a sartzeko

    // Datu Basearen konfigurazioa 
    private final String URL = "jdbc:mysql://localhost:3306/maltunaDB";
    private final String USER = "root";
    private final String PASS = "12345678";

    @FXML
    void makinaEzabatu(ActionEvent event) {
        String idTexto = txtIdEzabatu.getText().trim();

        // 1. Egiaztatu eremua hutsik dagoen
        if (idTexto.isEmpty()) {
            mostrarAlerta("Kontuz!", "Borratzeko ID bat sartu behar duzu.");
            return;
        }

        // 2. SQL Sententzia (Makinak taulan Id_makina erabiliz)
        String sql = "DELETE FROM makinak WHERE Id_makina = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(URL, USER, PASS); 
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                // Zure model-aren arabera Id-a String bada, setString erabili. 
                // Hemen setString erabiltzen dugu makinaren ID-ak testua izan daitezkeelako.
                pstmt.setString(1, idTexto);

                int filasBorradas = pstmt.executeUpdate();

                // 3. Emaitza egiaztatu
                if (filasBorradas > 0) {
                    mostrarAlerta("Ondo", "" + idTexto + " ID-a duen makina zuzen borratu da.");
                    txtIdEzabatu.clear();
                } else {
                    mostrarAlerta("Kontuz", "Ez da existitzen " + idTexto + "-a duen makinarik.");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            mostrarAlerta("Datu basearen errorea", e.getMessage());
        } catch (Exception e) {
            mostrarAlerta("Errorea", "Errore bat gertatu da: " + e.getMessage());
        }
    }

    @FXML
    void atzeraBueltatu(ActionEvent event) throws IOException {
        // App klasean gorde dugun aurreko erroa kargatzen du
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