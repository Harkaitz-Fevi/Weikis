
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Irakurri_erabiltzaileak {
    public static void main(String[] args) {
        String lerroa;
        Connection cn = null;

        try (BufferedReader br = new BufferedReader(new FileReader("data\\erabiltzaileak.csv"))) {

            DBKonexioa konex = new DBKonexioa();
            cn = konex.konektatu();

            br.readLine(); // goiburua

            String kontsulta = "INSERT INTO ERABILTZAILEAK VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement agindua = cn.prepareStatement(kontsulta);

            while ((lerroa = br.readLine()) != null) {
                String[] datuak = lerroa.split(",");

                agindua.setInt(1, Integer.parseInt(datuak[0]));
                agindua.setString(2, datuak[1]);
                agindua.setString(3, datuak[2]);
                agindua.setString(4, datuak[3]);
                agindua.setString(5, datuak[4]);
                agindua.setInt(6, Integer.parseInt(datuak[5]));
                agindua.setString(7, datuak[6]);
                agindua.setDate(8, java.sql.Date.valueOf(datuak[7]));
                agindua.setDate(9, java.sql.Date.valueOf(datuak[8]));

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