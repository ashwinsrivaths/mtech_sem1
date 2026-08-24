
import java.sql.Connection;
import java.sql.DriverManager;

public class TestJdbc {

    public static void main(String[] args) {
        try {

            System.out.println("Hello world!");

            String url = "jdbc:hsqldb:hsql://localhost/";
            String uid = "SA";
            String pass = "";

            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            Connection con = DriverManager.getConnection(url, uid, pass);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}