package programa.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import programa.App;

public class PiezaAldatuController {

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

    private final String URL = "jdbc:mysql://localhost:3306/maltunaDB";
    private final String USER = "root";
    private final String PASS = "12345678";

    @FXML
    void piezaGehitu(ActionEvent event) { 

        if (txtId.getText().isEmpty() || txtIzena.getText().isEmpty()
                || txtPisua.getText().isEmpty() || txtPrezioa.getText().isEmpty()) {

            mostrarAlerta("Errorea", "Mesedez, bete zenbakizko eremu guztiak.");
            return; // Gelditu exekuzioa sistemaren errrorea gerta ez dadin.
        }

        String sql = "UPDATE piezak SET Izena = ?, Pisua = ?, Prezioa = ?, Deskribapena = ? WHERE Id_pieza = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, txtIzena.getText());
            pstmt.setInt(2, Integer.parseInt(txtPisua.getText()));
            pstmt.setDouble(3, Double.parseDouble(txtPrezioa.getText()));
            pstmt.setString(4, txtDeskribapena.getText());
            pstmt.setInt(5, Integer.parseInt(txtId.getText()));

            int filas = pstmt.executeUpdate();

            if (filas > 0) {
                mostrarAlerta("Ondo!", "¡Pieza zuzen aktualizatu da");
                App.setRoot("Piezak"); 
            } else {
                mostrarAlerta("Errorea", "Ez da existitzen hurrengo Id-a duen pieza bat: " + txtId.getText());
            }

        } catch (Exception e) {
            mostrarAlerta("Errorea", "Arazoak aktualizatzerakoan: " + e.getMessage());
        }
    }

    @FXML
    void atzeraBueltatu(ActionEvent event) throws IOException {
        App.setRoot("Piezak");
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
