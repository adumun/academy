import app.TestConnection;
import database.DatabaseConnection;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class JdbcConnectionTest {
    private static final String URL = "jdbc:mariadb://localhost:3306/preparacion_db";

    @Test
    void conexionValidaYEsquema() throws Exception {
        try (var con = DatabaseConnection.getConnection();
             var statement = con.createStatement();
             var rs = statement.executeQuery("SELECT DATABASE(), CURRENT_USER(), VERSION()")) {
            assertTrue(con.isValid(3));
            assertTrue(rs.next());
            assertEquals("preparacion_db", rs.getString(1));
            assertTrue(rs.getString(2).startsWith("academy_lab@"));
            assertTrue(rs.getString(3).toLowerCase(Locale.ROOT).contains("mariadb"));
            assertFalse(rs.next());
        }
    }

    @Test
    void salidaDocumentada() throws Exception {
        var original = System.out;
        var bytes = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(bytes, true, StandardCharsets.UTF_8));
            TestConnection.main(new String[0]);
        } finally {
            System.setOut(original);
        }
        assertEquals("Conexión válida: true", bytes.toString(StandardCharsets.UTF_8).strip());
    }

    @Test
    void credencialesInvalidas() {
        var error = assertThrows(SQLException.class, () -> {
            try (var ignored = DriverManager.getConnection(URL, System.getenv("DB_USER"),
                    System.getenv("DB_PASSWORD") + "-incorrecta")) {
                fail("No debe autenticar");
            }
        });
        assertEquals("28000", error.getSQLState());
    }

    @Test
    void permisosMinimos() throws Exception {
        try (var con = DatabaseConnection.getConnection();
             var statement = con.createStatement();
             var rs = statement.executeQuery("SHOW GRANTS")) {
            var grants = new StringBuilder();
            while (rs.next()) grants.append(rs.getString(1).toUpperCase(Locale.ROOT)).append('\n');
            var text = grants.toString();
            for (var privilege : new String[]{"SELECT", "INSERT", "UPDATE", "DELETE"}) {
                assertTrue(text.contains(privilege), privilege);
            }
            assertFalse(text.contains("ALL PRIVILEGES"));
            assertFalse(text.contains("WITH GRANT OPTION"));
            var error = assertThrows(SQLException.class, () ->
                    statement.executeUpdate("CREATE TABLE no_autorizada (id INT)"));
            assertEquals(1142, error.getErrorCode());
        }
    }

    @Test
    void lecturaTablaYRecursos() throws Exception {
        Connection con = DatabaseConnection.getConnection();
        try (con; var statement = con.prepareStatement("SELECT id, titulo FROM libro ORDER BY id");
             var rs = statement.executeQuery()) {
            assertFalse(rs.next());
        }
        assertTrue(con.isClosed());
    }
}
