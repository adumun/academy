package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mariadb://localhost:3306/preparacion_db";
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        if (user == null || user.isBlank() || password == null) {
            throw new SQLException("Configura DB_USER y DB_PASSWORD");
        }

        return DriverManager.getConnection(url, user, password);
    }
}
