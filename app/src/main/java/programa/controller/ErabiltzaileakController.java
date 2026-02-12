package programa.controller;

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
import programa.model.Erabiltzailea;

public class ErabiltzaileakController {

    @FXML
    private TableView<Erabiltzailea> erabiltzaileak; 

    @FXML private TableColumn<Erabiltzailea, Integer> colId;
    @FXML private TableColumn<Erabiltzailea, String> colIzena;
    @FXML private TableColumn<Erabiltzailea, String> colAbizena;
    @FXML private TableColumn<Erabiltzailea, String> colNAN;
    @FXML private TableColumn<Erabiltzailea, String> colHelbidea;
    @FXML private TableColumn<Erabiltzailea, Integer> colPosta;
    @FXML private TableColumn<Erabiltzailea, String> colEmail;
    @FXML private TableColumn<Erabiltzailea, String> colJaiotze;
    @FXML private TableColumn<Erabiltzailea, String> colAlta;

    private final String URL = "jdbc:mysql://localhost:3306/maltunaDB";
    private final String USER = "root";
    private final String PASS = "12345678";

    @FXML
    public void initialize() {
        // Vinculación con los Getters de Erabiltzailea.java
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colIzena.setCellValueFactory(new PropertyValueFactory<>("izena"));
        colAbizena.setCellValueFactory(new PropertyValueFactory<>("abizena"));
        colNAN.setCellValueFactory(new PropertyValueFactory<>("nan"));
        colHelbidea.setCellValueFactory(new PropertyValueFactory<>("helbidea"));
        colPosta.setCellValueFactory(new PropertyValueFactory<>("posta"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colJaiotze.setCellValueFactory(new PropertyValueFactory<>("jaiotze"));
        colAlta.setCellValueFactory(new PropertyValueFactory<>("alta"));

        cargarDatos();
    }

    public void cargarDatos() {
        ObservableList<Erabiltzailea> lista = FXCollections.observableArrayList();
        String sql = "SELECT Id_erabiltzaileak, Izena, Abizena1, NAN, Helbidea, Posta_kodea, Email, Jaiotze_data, Alta_data FROM erabiltzaileak";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Erabiltzailea(
                    rs.getInt("Id_erabiltzaileak"),
                    rs.getString("Izena"),
                    rs.getString("Abizena1"),
                    rs.getString("NAN"),
                    rs.getString("Helbidea"),
                    rs.getInt("Posta_kodea"),
                    rs.getString("Email"),
                    rs.getString("Jaiotze_data"),
                    rs.getString("Alta_data")
                ));
            }
            erabiltzaileak.setItems(lista);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void informazioaAldatu() {
        try {
            App.setPreviousRoot("Erabiltzaileak");
            App.setRoot("Erabiltzailea_informazioa_aldatu");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void gehituErabiltzailea() {
        try {
            App.setPreviousRoot("Erabiltzaileak");
            App.setRoot("Erabiltzailea_gehitu");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void kenduErabiltzailea() {
        try {
            App.setPreviousRoot("Erabiltzaileak");
            App.setRoot("erabiltzailea_ezabatu");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}