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

public class PiezaGehituController {

    // FXML-an jarri ditugun fx:id-en erreferentziak
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtIzena;
    @FXML
    private TextField txtPisua;
    @FXML
    private TextField txtPrezioa;
    @FXML
    private TextField txtDeskribapena;

    // Datu basuaren konfigurazioa
    private final String URL = "jdbc:mysql://localhost:3306/maltunaDB";
    private final String USER = "root";
    private final String PASS = "12345678";

    @FXML
    void piezaGehitu(ActionEvent event) {
        // 1. SQL Sententzia
        String sql = "INSERT INTO piezak (Id_pieza, Izena, Pisua, Prezioa, Deskribapena) VALUES (?, ?, ?, ?, ?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(URL, USER, PASS); PreparedStatement pstmt = conn.prepareStatement(sql)) {

                // 2. Datuak jaso eta prestatu SQL-rako
                // Id_pieza  INT 
                pstmt.setInt(1, Integer.parseInt(txtId.getText()));

                // Izena  VARCHAR
                pstmt.setString(2, txtIzena.getText());

                // Pisua  INT 
                pstmt.setInt(3, Integer.parseInt(txtPisua.getText()));

                // Prezioa DECIMAL
                pstmt.setBigDecimal(4, new java.math.BigDecimal(txtPrezioa.getText()));

                // Deskribapena  VARCHAR 
                pstmt.setString(5, txtDeskribapena.getText());

                pstmt.executeUpdate();

                mostrarAlerta("Ondo!", "Pieza zuzen gorde da.");
                limpiarCampos();
            }

        } catch (SQLException e) {
            mostrarAlerta("SQL Errorea", "Egiaztatu zutabeen izenak: " + e.getMessage());
        } catch (NumberFormatException e) {
            mostrarAlerta("Formatu arazoak", "Id-a eta pisua zenbaki osoak izan behar dira, eta prezioa zenbaki osoa edo komaz banatutako zenbakia.");
        } catch (Exception e) {
            mostrarAlerta("Errorea", "Errore bat gertatu da: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        txtId.clear();
        txtIzena.clear();
        txtPisua.clear();
        txtPrezioa.clear();
        txtDeskribapena.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
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
