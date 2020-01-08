package dbconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;




public class DbConn {

    public final static String MEDICALRECORD = "medical_record";
    public final static String MEDICAL_CACHE = "medical_record_cache";

    public static Connection getConnection(String dbName) {
        return getConnection(dbName, "islab", "123");
    }

    public static Connection getConnection(String dbName, String userName, String password) {
        try {
            //String url = "jdbc:mysql://localhost:3306/" + dbName + "?user="+userName+"&password="+password+"&useUnicode=true&characterEncoding=UTF-8";
            String url = "jdbc:mysql://140.124.181.23:8787/" + dbName + "?serverTimezone=UTC";
            //return DriverManager.getConnection(url);
            com.mysql.cj.jdbc.Driver driver = new com.mysql.cj.jdbc.Driver();
            return DriverManager.getConnection("jdbc:mysql://140.124.181.23:8787/" + dbName + "?user=" + userName + "&password=" + password + "&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC");
        }catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return null;
    }
}
