
<<<<<<< HEAD
import java.sql.Connection;   
=======
import java.sql.Connection;
>>>>>>> dev
import java.sql.DriverManager;   
import java.sql.SQLException;    


public class DBKonexioa {

    private static final String URL = "jdbc:mysql://localhost:3306/maltunaDB";
    private static final String USER = "root";
<<<<<<< HEAD
    private static final String PASS = "72546888N";
=======
    private static final String PASS = "72587073Q";
>>>>>>> dev

    public Connection konektatu() throws SQLException {
        return  DriverManager.getConnection(URL, USER, PASS);
    }
<<<<<<< HEAD
}


=======
}
>>>>>>> dev
