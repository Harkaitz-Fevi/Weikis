import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Irakurri_erabiltzaileak_eta_makinak{

    public static void main(String[] args) {
        String lerroa;

        try (BufferedReader br = new BufferedReader(new FileReader("data/erabiltzaileak_eta_makinak.csv"))) {
            br.readLine(); //Goiburua saltatzeko

                // != null esan nahi du, ejekutatu egingo dela irakurtzeko lerroak dauden bitartean
                // .csv-ko lerroak irakurtzen ditu amaierararte 
            while ((lerroa = br.readLine()) != null) {
                String[] datuak = lerroa.split(",", -1);
                System.out.println(datuak[0] + "-" + datuak[1] + "-" + datuak[2]+ "-" + datuak[3]);
            }
        } catch (IOException e) {
            System.out.println("Errorea fitxategia irakurtzean.");
        }
    }
}