package programa.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import programa.App;

public class Makina_aldatuController {

    @FXML private TextField idMakina;
    @FXML private TextField idizena;
    @FXML private TextField iddeskribapena;
    @FXML private TextField idpotentzia;
    @FXML private TextField idinstalazioa;

    private final String URL = "jdbc:mysql://localhost:3306/maltunaDB";
    private final String USER = "root";
    private final String PASS = "12345678";

    private Connection establecerConexion() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            mostrarAlerta("Konexio Errorea", "Ezin izan da datu-basera konektatu.", AlertType.ERROR);
            return null;
        }
    }

    /**
     * Busca la máquina por ID y rellena los campos.
     * Se activa al pulsar ENTER en el campo idMakina.
     */
    @FXML
    void buscarMakina() {
        if (idMakina.getText().isEmpty()) {
            mostrarAlerta("Oharra", "Mesedez, sartu ID bat bilatzeko.", AlertType.WARNING);
            return;
        }

        String sql = "SELECT * FROM makinak WHERE Id_makina = ?";
        
        try (Connection conn = establecerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            if (conn == null) return;

            pstmt.setString(1, idMakina.getText());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                idizena.setText(rs.getString("Izena"));
                iddeskribapena.setText(rs.getString("Deskribapena"));
                idpotentzia.setText(rs.getString("Potentzia"));
                idinstalazioa.setText(rs.getString("Instalazio_data"));
            } else {
                mostrarAlerta("Oharra", "Ez da aurkitu ID hori duen makinarik.", AlertType.WARNING);
                garbituEremuak(false);
            }
        } catch (SQLException e) {
            mostrarAlerta("Errorea", "Errorea bilatzerakoan: " + e.getMessage(), AlertType.ERROR);
        }
    }

    /**
     * Ejecuta el UPDATE en la base de datos.
     */
    @FXML
    void aldatu() {
        if (idMakina.getText().isEmpty()) {
            mostrarAlerta("Errorea", "IDa beharrezkoa da eguneratzeko.", AlertType.ERROR);
            return;
        }

        String sql = "UPDATE makinak SET Izena=?, Deskribapena=?, Potentzia=?, Instalazio_data=? WHERE Id_makina=?";

        try (Connection conn = establecerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            if (conn == null) return;

            pstmt.setString(1, idizena.getText());
            pstmt.setString(2, iddeskribapena.getText());
            pstmt.setString(3, idpotentzia.getText());
            pstmt.setString(4, idinstalazioa.getText());
            pstmt.setString(5, idMakina.getText());

            int resultado = pstmt.executeUpdate();
            
            if (resultado > 0) {
                mostrarAlerta("Eginda", "Makina ondo eguneratu da!", AlertType.INFORMATION);
            } else {
                mostrarAlerta("Errorea", "Ezin izan da eguneratu. Ziurtatu IDa zuzena dela.", AlertType.ERROR);
            }

        } catch (SQLException e) {
            mostrarAlerta("Errorea", "Datu-basean errorea: " + e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    private void atzeraBueltatu() throws IOException {
        App.setRoot(App.getPreviousRoot()); // Usa App.getPreviousRoot() según tu lógica
    }

    private void garbituEremuak(boolean limpiarId) {
        if (limpiarId) idMakina.clear();
        idizena.clear();
        iddeskribapena.clear();
        idpotentzia.clear();
        idinstalazioa.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
