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

public class Erabiltzailea_aldatuController {

    @FXML private TextField txtId;
    @FXML private TextField txtIzena;
    @FXML private TextField txtAbizena;
    @FXML private TextField txtNAN;
    @FXML private TextField txtHelbidea;
    @FXML private TextField txtPostaKodea;
    @FXML private TextField txtEmail;
    @FXML private TextField txtJaiotzeData;
    @FXML private TextField txtAltaData;

    /**
     * Establece la conexión con la base de datos maltunaDB.
     */
    private Connection establecerConexion() {
        String url = "jdbc:mysql://localhost:3306/maltunaDB"; 
        String user = "root"; 
        String pass = "12345678"; // Tu contraseña configurada
        try {
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            mostrarAlerta("Konexio Errorea", "Ezin izan da datu-basera konektatu.", AlertType.ERROR);
            return null;
        }
    }

    /**
     * Paso 1: Busca al usuario por su ID y rellena los campos automáticamente.
     * Este método se activa al pulsar ENTER en el campo txtId.
     */
    @FXML
    void buscarErabiltzaile() {
        if (txtId.getText().isEmpty()) {
            mostrarAlerta("Oharra", "Mesedez, sartu ID bat bilatzeko.", AlertType.WARNING);
            return;
        }

        String sql = "SELECT * FROM erabiltzaileak WHERE Id_erabiltzaileak = ?";
        
        try (Connection conn = establecerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            if (conn == null) return;

            pstmt.setInt(1, Integer.parseInt(txtId.getText()));
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Rellenamos los campos con los datos actuales de la DB
                txtIzena.setText(rs.getString("Izena"));
                txtAbizena.setText(rs.getString("Abizena1"));
                txtNAN.setText(rs.getString("NAN"));
                txtHelbidea.setText(rs.getString("Helbidea"));
                txtPostaKodea.setText(String.valueOf(rs.getInt("Posta_kodea")));
                txtEmail.setText(rs.getString("Email"));
                txtJaiotzeData.setText(rs.getString("Jaiotze_data"));
                txtAltaData.setText(rs.getString("Alta_data"));
            } else {
                mostrarAlerta("Oharra", "Ez da aurkitu ID hori duen erabiltzailerik.", AlertType.WARNING);
                garbituEremuak(false); // Limpia todo menos el ID
            }
        } catch (SQLException | NumberFormatException e) {
            mostrarAlerta("Errorea", "Errorea bilatzerakoan: " + e.getMessage(), AlertType.ERROR);
        }
    }

    /**
     * Paso 2: Ejecuta el UPDATE para guardar los cambios realizados.
     * Este método se activa al pulsar el botón "Gorde aldaketak".
     */
    @FXML
    void aldatu() {
        if (txtId.getText().isEmpty()) {
            mostrarAlerta("Errorea", "IDa beharrezkoa da eguneratzeko.", AlertType.ERROR);
            return;
        }

        String sql = "UPDATE erabiltzaileak SET Izena=?, Abizena1=?, NAN=?, Helbidea=?, Posta_kodea=?, Email=?, Jaiotze_data=?, Alta_data=? WHERE Id_erabiltzaileak=?";

        try (Connection conn = establecerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            if (conn == null) return;

            pstmt.setString(1, txtIzena.getText());
            pstmt.setString(2, txtAbizena.getText());
            pstmt.setString(3, txtNAN.getText());
            pstmt.setString(4, txtHelbidea.getText());
            pstmt.setInt(5, Integer.parseInt(txtPostaKodea.getText()));
            pstmt.setString(6, txtEmail.getText());
            pstmt.setString(7, txtJaiotzeData.getText());
            pstmt.setString(8, txtAltaData.getText());
            pstmt.setInt(9, Integer.parseInt(txtId.getText()));

            int resultado = pstmt.executeUpdate();
            
            if (resultado > 0) {
                mostrarAlerta("Eginda", "Informazioa ondo eguneratu da!", AlertType.INFORMATION);
            } else {
                mostrarAlerta("Errorea", "Ezin izan da eguneratu. Ziurtatu IDa zuzena dela.", AlertType.ERROR);
            }

        } catch (SQLException | NumberFormatException e) {
            mostrarAlerta("Errorea", "Datu-basean errorea: " + e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    private void atzeraBueltatu() throws IOException {
        App.setRoot(App.getPreviousRoot());
    }

    private void garbituEremuak(boolean limpiarId) {
        if (limpiarId) txtId.clear();
        txtIzena.clear();
        txtAbizena.clear();
        txtNAN.clear();
        txtHelbidea.clear();
        txtPostaKodea.clear();
        txtEmail.clear();
        txtJaiotzeData.clear();
        txtAltaData.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}