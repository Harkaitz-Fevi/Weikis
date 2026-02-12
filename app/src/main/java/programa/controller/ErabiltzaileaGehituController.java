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

public class ErabiltzaileaGehituController {

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtIzena;
    @FXML
    private TextField txtAbizena;
    @FXML
    private TextField txtNAN;
    @FXML
    private TextField txtHelbidea;
    @FXML
    private TextField txtPostaKodea;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtJaiotzeData;
    @FXML
    private TextField txtAltaData;

    private Connection establecerConexion() {
        // Asegúrate de que el nombre de la BD y credenciales coincidan con las tuyas
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
    void gehitu() {
        String sql = "INSERT INTO erabiltzaileak (Id_erabiltzaileak, Izena, Abizena1, NAN, Helbidea, Posta_kodea, Email, Jaiotze_data, Alta_data) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = establecerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn == null) {
                throw new SQLException("Ezin da konektatu");
            }

            pstmt.setInt(1, Integer.parseInt(txtId.getText()));
            pstmt.setString(2, txtIzena.getText());
            pstmt.setString(3, txtAbizena.getText());
            pstmt.setString(4, txtNAN.getText());
            pstmt.setString(5, txtHelbidea.getText());
            pstmt.setInt(6, Integer.parseInt(txtPostaKodea.getText()));
            pstmt.setString(7, txtEmail.getText());
            pstmt.setString(8, txtJaiotzeData.getText());
            pstmt.setString(9, txtAltaData.getText());

            pstmt.executeUpdate();
            mostrarAlerta("Eginda", "Erabiltzailea ondo gorde da!", AlertType.INFORMATION);
            garbituEremuak();

        } catch (SQLException e) {
            mostrarAlerta("Errorea", "Datu-basean errorea: " + e.getMessage(), AlertType.ERROR);
        } catch (NumberFormatException e) {
            mostrarAlerta("Errorea", "Id eta Posta kodea zenbakiak izan behar dira.", AlertType.WARNING);
        }
    }

    private void garbituEremuak() {
        txtId.clear();
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

        @FXML
    private void atzeraBueltatu() throws IOException {
        App.setRoot(App.getPreviousRoot());
    }
    
}
