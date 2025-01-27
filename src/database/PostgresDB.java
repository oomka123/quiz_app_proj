package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDB {
    private String host;
    private String username;
    private String password;
    private String dbName;
    private Connection connection;

    public PostgresDB(String host, String username, String password, String dbName) {
        this.setHost(host);
        this.setUsername(username);
        this.setPassword(password);
        this.setDbName(dbName);
    }

    public Connection getConnection() {
        String connectionUrl = this.host + "/" + this.dbName;

        try {
            if (this.connection != null && !this.connection.isClosed()) {
                return this.connection;
            } else {
                Class.forName("org.postgresql.Driver");
                this.connection = DriverManager.getConnection(connectionUrl, this.username, this.password);
                return this.connection;
            }
        } catch (Exception e) {
            System.out.println("failed to connect to postgres: " + e.getMessage());
            return null;
        }
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDbName() {
        return this.dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public void close() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException ex) {
                System.out.println("Connection close error: " + ex.getMessage());
            }
        }

    }
}