
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Irakurri_erabiltzaileak {

    public static void main(String[] args) {
        String lerroa;

        try (BufferedReader br = new BufferedReader(new FileReader("data/erabiltzaileak.csv"))) {
            br.readLine(); //Goiburua saltatzeko

                // != null esan nahi du, ejekutatu egingo dela irakurtzeko lerroak dauden bitartean
                // .csv-ko lerroak irakurtzen ditu amaierararte 
            while ((lerroa = br.readLine()) != null) {
                String[] datuak = lerroa.split(",");
                System.out.println(datuak[0] + "-" + datuak[1] + "-" + datuak[2] + "-" + datuak[3]+ "-" + datuak[4]+ "-" + datuak[5] + "-" + datuak[6] + "-" + datuak[7] + "-" + datuak[8]);
            }
        } catch (IOException e) {
            System.out.println("Errorea fitxategia irakurtzean.");
        }
    }
}