import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Irakurri_makinak {

    public static void main(String[] args) {
        String lerroa;
        Connection cn = null;

        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\web25olorente\\Documents\\Garapen-Inguruneak\\WEIKIS\\Weikis\\data\\makinak.csv"))) {
            DBKonexioa konex = new DBKonexioa();
            cn = konex.konektatu();

            br.readLine(); // Goiburua saltatzeko

            String kontsulta = "INSERT INTO MAKINAK VALUES(?,?,?,?,?)";
            PreparedStatement agindua = cn.prepareStatement(kontsulta);

            // != null esan nahi du, ejekutatu egingo dela irakurtzeko lerroak dauden bitartean
            // .csv-ko lerroak irakurtzen ditu amaierararte
            while ((lerroa = br.readLine()) != null) {
                String[] datuak = lerroa.split(",");
                System.out.println(datuak[0] + "-" + datuak[1] + "-" + datuak[2] + "-" + datuak[3] + "-" + datuak[4]);

                agindua.setInt(1, Integer.parseInt(datuak[0]));
                agindua.setString(2, datuak[1]);
                agindua.setString(3, datuak[2]);
                agindua.setInt(4, Integer.parseInt(datuak[3]));
                agindua.setDate(5, java.sql.Date.valueOf(datuak[4]));

                agindua.executeUpdate();
            }

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