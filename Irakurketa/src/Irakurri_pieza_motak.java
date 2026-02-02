import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Irakurri_pieza_motak {

    public static void main(String[] args) {
        String lerroa;
        Connection cn = null;

        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\web25olorente\\Documents\\Garapen-Inguruneak\\WEIKIS\\Weikis\\data\\pieza_motak.csv"))) {
            DBKonexioa konex = new DBKonexioa();
            cn = konex.konektatu();
            br.readLine(); // Goiburua saltatzeko

            // != null esan nahi du, ejekutatu egingo dela irakurtzeko lerroak dauden bitartean
            // .csv-ko lerroak irakurtzen ditu amaierararte
            String kontsulta = "INSERT INTO PIEZA_MOTAK VALUES(?,?)";
            PreparedStatement agindua = cn.prepareStatement(kontsulta);

            while ((lerroa = br.readLine()) != null) {
                String[] datuak = lerroa.split(",");
                System.out.println(datuak[0] + "-" + datuak[1]);

                agindua.setInt(1, Integer.parseInt(datuak[0]));
                agindua.setString(2, (datuak[1]));

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