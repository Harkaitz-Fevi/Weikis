package programa.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import programa.App;
import programa.model.pieza; // Model-ean "pieza" klasea sortuta izan behar da.
public class PiezakController {

    @FXML
    private TableView<pieza> erabiltzaileak; 

    // --- ZUTABE BERRIAK ---
    @FXML private TableColumn<pieza, Integer> colId;
    @FXML private TableColumn<pieza, String> colIzena;
    @FXML private TableColumn<pieza, String> colDesc;
    @FXML private TableColumn<pieza, Integer> colPisua;
    @FXML private TableColumn<pieza, Double> colPrezioa;

    // Datu basearen konfigurazioa
    private final String URL = "jdbc:mysql://localhost:3306/maltunaDB";
    private final String USER = "root";
    private final String PASS = "12345678";

    // --- Metodo honek taula kargatzen du irekitzerakoan ---
    @FXML
    public void initialize() {
        // Lotu zutabeak pieza.java-ren atributuetara.
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colIzena.setCellValueFactory(new PropertyValueFactory<>("izena"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("deskribapena"));
        colPisua.setCellValueFactory(new PropertyValueFactory<>("pisua"));
        colPrezioa.setCellValueFactory(new PropertyValueFactory<>("prezioa"));

        cargarDatos();
    }

    public void cargarDatos() {
        ObservableList<pieza> lista = FXCollections.observableArrayList();
        String sql = "SELECT Id_pieza, Izena, Pisua, Prezioa, Deskribapena FROM piezak";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new pieza(
                    rs.getInt("Id_pieza"),
                    rs.getString("Izena"),
                    rs.getInt("Pisua"),
                    rs.getDouble("Prezioa"),
                    rs.getString("Deskribapena")
                ));
            }
            erabiltzaileak.setItems(lista);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- METODOAK ---

    @FXML
    private void piezaKendu() {
        System.out.println("piezaKendu klikatuta");
        try {
            App.setPreviousRoot("Piezak");
            App.setRoot("Pieza_ezabatu");
        } catch (Exception e) {
            System.out.println("Errorea -Pieza ezabatu-, pantaila kargatzerakoan:");
            e.printStackTrace();
        }
    }

    @FXML
    private void informazioaAldatu() {
        try {
            App.setPreviousRoot("Piezak");
            App.setRoot("Pieza_informazioa_aldatu");
        } catch (Exception e) {
            System.out.println("Errorea -Pieza informazioa aldatu-, pantaila kargatzerakoan:");
            e.printStackTrace();
        }
    }

    @FXML
    private void piezaGehitu() {
        try {
            App.setPreviousRoot("Piezak");
            App.setRoot("Pieza_gehitu");
        } catch (Exception e) {
            System.out.println("Errorea -Pieza gehitu-, pantaila kargatzerakoan:");
            e.printStackTrace();
        }
    }

    @FXML
    private void makinakBistaratu() throws IOException {
        App.setRoot("Makinak");
    }

    @FXML
    private void piezakBistaratu() throws IOException {
        App.setRoot("Piezak");
    }

    @FXML
    private void erabiltzaileakBistaratu() throws IOException {
        App.setRoot("Erabiltzaileak");
    }
}