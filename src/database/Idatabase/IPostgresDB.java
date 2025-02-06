package database.Idatabase;

import java.sql.Connection;

public interface IPostgresDB {
    Connection getConnection();
    void close();
}
