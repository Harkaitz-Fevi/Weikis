import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Irakurri_erabiltzaileak_eta_makinak {

    public static void main(String[] args) {
        String lerroa;
        Connection cn = null;

        try (BufferedReader br = new BufferedReader(new FileReader("data/erabiltzaileak_eta_makinak.csv"))) {

            DBKonexioa konex = new DBKonexioa();
            cn = konex.konektatu();

            br.readLine(); // Goiburua saltatzeko

            String kontsulta = "INSERT INTO ERABILERA VALUES(?,?,?,?)";
            PreparedStatement agindua = cn.prepareStatement(kontsulta);

            // != null esan nahi du, ejekutatu egingo dela irakurtzeko lerroak dauden bitartean
            // .csv-ko lerroak irakurtzen ditu amaierararte
            while ((lerroa = br.readLine()) != null) {
                String[] datuak = lerroa.split(",", -1);
                System.out.println(datuak[0] + "-" + datuak[1] + "-" + datuak[2] + "-" + datuak[3]);

                agindua.setInt(1, Integer.parseInt(datuak[0]));
                agindua.setInt(2, Integer.parseInt(datuak[1]));
                agindua.setDate(3, java.sql.Date.valueOf(datuak[2]));
                agindua.setDate(4, java.sql.Date.valueOf(datuak[3]));

                agindua.executeUpdate();
            }

            agindua.close();

        } catch (SQLException e) {
            System.out.println("Errorea datuak sartzean.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Errorea fitxategia irakurtzean.");
        } finally {
            if (cn != null) {
                try {
                    cn.close();
                    System.out.println("Konexioa itxi da.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}