package programa.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import programa.App;
import programa.model.Makina;

public class MakinakController implements Initializable {

    // FXML-ko elementuak: ziurtatu MakinakCenter.fxml-eko fx:id-ekin bat datozela
    @FXML
    private TableView<Makina> tablaMakinak;
    @FXML
    private TableColumn<Makina, Integer> colId;
    @FXML
    private TableColumn<Makina, String> colIzena;
    @FXML
    private TableColumn<Makina, String> colDeskribapena;
    @FXML
    private TableColumn<Makina, Integer> colPotentzia;
    @FXML
    private TableColumn<Makina, String> colData;

    // Datu-basearen konfigurazioa
    private final String URL_DB = "jdbc:mysql://localhost:3306/maltunaDB";
    private final String USER = "root";
    private final String PASS = "12345678";

    /**
     * Pantaila kargatzean exekutatzen den metodoa.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Zutabeak eta Makina.java ereduaren atributuak lotu
        // PropertyValueFactory barruko izenek Makina klaseko "get" metodoekin bat etorri behar dute
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colIzena.setCellValueFactory(new PropertyValueFactory<>("izena"));
        colDeskribapena.setCellValueFactory(new PropertyValueFactory<>("deskribapena"));
        colPotentzia.setCellValueFactory(new PropertyValueFactory<>("potentzia"));
        colData.setCellValueFactory(new PropertyValueFactory<>("instalazioData"));

        // Datuak kargatu taulan
        cargarDatos();
    }

    /**
     * Datu-basetik makina guztiak irakurri eta TableView-an sartzen ditu.
     */
    private void cargarDatos() {
        ObservableList<Makina> lista = FXCollections.observableArrayList();
        String sql = "SELECT Id_makina, Izena, Deskribapena, Potentzia, Instalazio_data FROM makinak";

        try {
            // Driver-a kargatu (beharrezkoa bada)
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(URL_DB, USER, PASS);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    // Makina objektu berria sortu lerro bakoitzeko
                    lista.add(new Makina(
                        rs.getInt("Id_makina"),
                        rs.getString("Izena"),
                        rs.getString("Deskribapena"),
                        rs.getInt("Potentzia"),
                        rs.getString("Instalazio_data")
                    ));
                }
                // Zerrenda taulari esleitu
                tablaMakinak.setItems(lista);

            }
        } catch (ClassNotFoundException e) {
            System.err.println("Driverra ez da aurkitu!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("SQL Errorea datuak kargatzean: " + e.getMessage());
        }
    }

    /**
     * "Informazioa aldatu" botoia sakatzean aldatzeko leihora eramaten du.
     */
    @FXML
    private void informazioaAldatu() throws IOException {
        App.setRoot("Makina_informazioa_aldatu");
    }

    /**
     * "Gehitu" botoia sakatzean makina berria sartzeko leihora eramaten du.
     */
    @FXML
    private void gehituMakina() throws IOException {
        App.setRoot("Makina_gehitu");
    }

    /**
     * (Aukerakoa) Aukeratutako makina ezabatzeko metodoa.
     */
    @FXML
    private void kenduMakina() throws IOException {
        // Momentuz ezabatzeko leihora bidaltzen du
        App.setRoot("Makina_ezabatu");
    }
}