package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDB {
    private static volatile PostgresDB instance;
    private Connection connection;

    private final String host;
    private final String username;
    private final String password;
    private final String dbName;

    private PostgresDB(String host, String username, String password, String dbName) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.dbName = dbName;
        this.connection = createConnection();
    }

    // Метод для создания единственного экземпляра PostgresDB
    public static PostgresDB getInstance(String host, String username, String password, String dbName) {
        if (instance == null) {
            synchronized (PostgresDB.class) {
                if (instance == null) { // Double-checked locking
                    instance = new PostgresDB(host, username, password, dbName);
                }
            }
        }
        return instance;
    }

    // Метод для установления соединения с БД
    private Connection createConnection() {
        String connectionUrl = host + "/" + dbName;
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(connectionUrl, username, password);
        } catch (Exception e) {
            System.out.println("Failed to connect to PostgreSQL: " + e.getMessage());
            return null;
        }
    }

    // Метод для получения соединения (гарантирует, что соединение живо)
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = createConnection();
            }
        } catch (SQLException e) {
            System.out.println("Error checking connection: " + e.getMessage());
        }
        return connection;
    }

    // Закрытие соединения
    public void close() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                instance = null; // Позволяет пересоздать соединение, если потребуется
            } catch (SQLException ex) {
                System.out.println("Connection close error: " + ex.getMessage());
            }
        }
    }
}