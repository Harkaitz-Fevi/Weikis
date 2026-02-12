
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBKonexioa {

    private static final String URL = "jdbc:mysql://localhost:3306/maltunadb";
    private static final String USER = "root";
    private static final String PASS = "12345678";

    public Connection konektatu() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}