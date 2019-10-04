package segabank.dal;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PersistenceManager {
    private static final String PROPS_FILE = "./resources/db.properties";
    private static Connection connection;

    private PersistenceManager() {} // Prevents initialization

    public static Connection getConnection() throws SQLException, ClassNotFoundException, IOException {
        if (connection == null || connection.isClosed() || !connection.isValid(2)) {
            Properties props = new Properties();

            FileInputStream fis = new FileInputStream(PROPS_FILE);
            props.load(fis);

            String dbUrl = props.getProperty("jdbc.db.url");
            String dbLogin = props.getProperty("jdbc.db.login");
            String dbPwd = props.getProperty("jdbc.db.pwd");

            connection = DriverManager.getConnection(dbUrl, dbLogin, dbPwd);
        }
        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (!connection.isClosed() && connection.isValid(2)) {
            connection.close();
        }
    }
}
