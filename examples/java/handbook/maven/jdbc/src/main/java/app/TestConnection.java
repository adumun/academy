package app;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {

    public static void main(String[] args) throws SQLException {
        try (Connection con = DatabaseConnection.getConnection()) {
            System.out.println("Conexión válida: " + con.isValid(3));
        }
    }
}
