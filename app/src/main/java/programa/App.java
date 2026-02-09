package programa;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    // Lerroak "cachean"-an kargatu dira, (En la interfaz de usuario))
    private static final Map<String, Parent> rootCache = new HashMap<>();

    // Honekin gogoratuko da azkenengo pantailaz, beraz atzera bueltatu ahalko da.
    private static String previousRoot = "PrincipalBien";

    public static void setPreviousRoot(String root) {
        
        //Izendatutako atalerako une honetan erakusten den lerroa gorde, 'atzera'-k egoera leheneratzeko
        try {
            if (scene != null && scene.getRoot() != null) {
                rootCache.put(root, scene.getRoot());
            }
        } catch (Exception e) {
            System.out.println("[App] Abisua: Ezin izan da uneko lerroa cachean gorde " + root);
            e.printStackTrace();
        }
        previousRoot = root;
    }

    public static String getPreviousRoot() {
        return previousRoot == null ? "PrincipalBien" : previousRoot;
    }



    @Override
    public void start(Stage stage) {
        System.out.println("[App] Aplikazioa hasten, kargatzen PrincipalBien.fxml");
        try {
            Parent root = loadFXML("PrincipalBien");
            scene = new Scene(root, 640, 480);
            // Hasierako lerroa cachean gorde itzultzean bere egoera mantentzeko
            rootCache.put("PrincipalBien", root);
            scene.getStylesheets().add(App.class.getResource("/programa/principal.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Principal");
            stage.show();
            System.out.println("[App] PrincipalBien kargatuta");
        } catch (Exception e) {
            System.out.println("[App] Arazoak aplikazioa hasten:");
            e.printStackTrace();
        }
    }

    public static void setRoot(String fxml) {
        System.out.println("[App] setRoot -> " + fxml);
        try {
            Parent root = rootCache.get(fxml);
            if (root == null) {
                root = loadFXML(fxml);
                rootCache.put(fxml, root);
            }
            scene.setRoot(root);
        } catch (IOException e) {
            System.out.println("[App] Arazoak FXML-a sortzen: " + fxml);
            e.printStackTrace();
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}