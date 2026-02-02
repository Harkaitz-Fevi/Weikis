
import java.sql.Connection;
import java.sql.SQLException;

public class ProbaKonexioa {

    public static void main(String[] args) {

        DBKonexioa konex = new DBKonexioa();
        Connection cn;

        try {
            System.out.println("Sartu da 1");
            cn = konex.konektatu();
            System.out.println("Sartu da 2");

            if (cn != null && !cn.isClosed()) {
                System.out.println("Komunikazio kanala irekita dago.");
                cn.close();
                System.out.println("Konexioa itxi da.");
            }

        } catch (SQLException e) {
            System.out.println("Errorea datu-basera konektatzean");
            System.out.println(e.getMessage());
        }
    }
}
