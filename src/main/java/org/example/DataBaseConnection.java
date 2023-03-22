package org.example;
import java.sql.*;

public class DataBaseConnection {
    private final String url;
    private final String user;
    private final String password;

    public DataBaseConnection(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void close(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}

