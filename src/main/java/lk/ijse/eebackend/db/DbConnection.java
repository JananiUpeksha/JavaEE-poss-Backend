package lk.ijse.eebackend.db;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DbConnection {
    private static DbConnection dbConnection;
    private DataSource dataSource;

    private DbConnection() throws SQLException, NamingException {
        try {
            var ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/webPoss");

            if (dataSource != null) {
                System.out.println("DataSource lookup successful.");
            } else {
                throw new NamingException("Failed to lookup DataSource.");
            }
        } catch (NamingException e) {
            throw new SQLException("Error initializing database connection: " + e.getMessage(), e);
        }
    }

    public static synchronized DbConnection getInstance() throws SQLException, NamingException {
        if (dbConnection == null) {
            dbConnection = new DbConnection();
        }
        return dbConnection;
    }

    public Connection getConnection() throws SQLException {
        if (dataSource != null) {
            return dataSource.getConnection();
        } else {
            throw new SQLException("DataSource is not initialized.");
        }
    }
}
