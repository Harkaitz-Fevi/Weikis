
import java.sql.Connection;
import java.sql.SQLException;

public class ProbaKonexioa {

    public static void main(String[] args) {

        DBKonexioa konex = new DBKonexioa();
        Connection cn = null;

        try {
            system.out.print.ln("Sartu da 1");
            cn =konex.konektatu();
            system.out.print.ln("Sartu da 2");
            
            
            if (cn != null && !cn.isClosed()) {
                System.out.println("Komunikazio kanala irekita dago.");
                cn.close();
                System.out.println("Konexioa itxi da.");
            }

        } catch (SQLException e) {
            System.out.println("Errorea datu-basera konektatzean");
            e.printStackTrace();
        }
    }
}

