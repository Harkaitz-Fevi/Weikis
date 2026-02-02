import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Irakurri_piezak_eta_pieza_motak {

    public static void main(String[] args) {
        String lerroa;

        try (BufferedReader br = new BufferedReader(new FileReader("data/piezak_eta_pieza_motak.csv"))) {
            br.readLine(); // Goiburua saltatzeko

            // != null esan nahi du, ejekutatu egingo dela irakurtzeko lerroak dauden bitartean
            // .csv-ko lerroak irakurtzen ditu amaierararte
            while ((lerroa = br.readLine()) != null) {
                String[] datuak = lerroa.split(",");
                System.out.println(datuak[0] + "-" + datuak[1]);
            }
        } catch (IOException e) {
            System.out.println("Errorea fitxategia irakurtzean.");
        }
    }
}