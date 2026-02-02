
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Irakurketa.src.DBKonexioa;

public class Kontsulta {

    public static void main(String[] args) {

        DBKonexioa konex = new DBKonexioa();

        try {

            Connection cn = konex.konektatu();

            if (cn != null && !cn.isClosed()) {
                System.out.println("Komunikazio kanala irekita dago.");
                String kontsulta = "SELECT Id_erabiltzaileak, Izena FROM erabiltzaileak";
                PreparedStatement agindua = cn.prepareStatement(kontsulta);
                ResultSet emaitza = agindua.executeQuery();

                while (emaitza.next()) {
                    String id = emaitza.getString("Id_erabiltzaileak");
                    String izena = emaitza.getString("Izena");
                    System.out.println(id + " " + izena);
                }

                emaitza.close();

                cn.close();
                System.out.println("Konexioa itxi da.");
            }

        } catch (SQLException e) {

            System.out.println("Errorea kontsulta exekutatzean");
            e.printStackTrace();
        }
    }
}
