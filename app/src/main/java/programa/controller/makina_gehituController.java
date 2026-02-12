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

public class makina_gehituController {

    // FXML-an jarri ditugun fx:id-en erreferentziak (zure FXML-arekin bat datoz)
    @FXML
    private TextField idMakina;
    @FXML
    private TextField idizena;
    @FXML
    private TextField iddeskribapena;
    @FXML
    private TextField idpotentzia;
    @FXML
    private TextField idinstalazioa;

    // Datu basuaren konfigurazioa
    private final String URL = "jdbc:mysql://localhost:3306/maltunaDB";
    private final String USER = "root";
    private final String PASS = "12345678";

    @FXML
    void makinaGehitu(ActionEvent event) {
        // 1. SQL Sententzia (Zutabeen izenak: Id_makina, Izena, Deskribapena, Potentzia, Instalazio_data)
        String sql = "INSERT INTO makinak (Id_makina, Izena, Deskribapena, Potentzia, Instalazio_data) VALUES (?, ?, ?, ?, ?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(URL, USER, PASS); 
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                // 2. Datuak jaso eta prestatu SQL-rako
                // Id_makina (Zure FXML-an idMakina da)
                pstmt.setString(1, idMakina.getText());

                // Izena
                pstmt.setString(2, idizena.getText());

                // Deskribapena
                pstmt.setString(3, iddeskribapena.getText());

                // Potentzia (String gisa tratatua zure model-aren arabera)
                pstmt.setString(4, idpotentzia.getText());

                // Instalazio_data
                pstmt.setString(5, idinstalazioa.getText());

                pstmt.executeUpdate();

                mostrarAlerta("Ondo!", "Makina zuzen gorde da.");
                limpiarCampos();
                
                // Aukerakoa: Taula nagusira itzuli gordetzerakoan
                // App.setRoot("Makinak");

            }

        } catch (SQLException e) {
            mostrarAlerta("SQL Errorea", "Egiaztatu zutabeen izenak edo Id-a errepikatuta dagoela: " + e.getMessage());
        } catch (Exception e) {
            mostrarAlerta("Errorea", "Errore bat gertatu da: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        idMakina.clear();
        idizena.clear();
        iddeskribapena.clear();
        idpotentzia.clear();
        idinstalazioa.clear();
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