package programa.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import programa.App;

public class erabiltzailea_ezabatuController {

    @FXML
    private TextField txtIdEzabatu;

    // Conexión a la base de datos (ajusta tus credenciales)
    private Connection establecerConexion() {
        String url = "jdbc:mysql://localhost:3306/maltunaDB"; 
        String user = "root"; 
        String pass = "12345678";     
        try {
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            return null;
        }
    }

    @FXML
    private void ezabatu() {
        String idParaBorrar = txtIdEzabatu.getText();

        if (idParaBorrar.isEmpty()) {
            mostrarAlerta("Errorea", "Mesedez, sartu Id bat ezabatzeko.", AlertType.WARNING);
            return;
        }

        // SQL basado en tu tabla 'erabiltzaileak'
        String sql = "DELETE FROM erabiltzaileak WHERE Id_erabiltzaileak = ?";

        try (Connection conn = establecerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            if (conn == null) throw new SQLException("Ezin da datu-basera konektatu.");

            pstmt.setInt(1, Integer.parseInt(idParaBorrar));
            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                mostrarAlerta("Eginda", "Erabiltzailea ondo ezabatu da.", AlertType.INFORMATION);
                txtIdEzabatu.clear();
            } else {
                mostrarAlerta("Oharra", "Ez da aurkitu Id hori duen erabiltzailerik.", AlertType.WARNING);
            }

        } catch (SQLException e) {
            mostrarAlerta("Errorea", "Datu-basean errorea: " + e.getMessage(), AlertType.ERROR);
        } catch (NumberFormatException e) {
            mostrarAlerta("Errorea", "Id-ak zenbakia izan behar du.", AlertType.ERROR);
        }
    }

    @FXML
    private void atzeraBueltatu() throws IOException {
        // Mantenemos tu lógica original de navegación
        App.setRoot(App.getPreviousRoot());
    }

    private void mostrarAlerta(String titulo, String mensaje, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}