import java.sql.SQLException;
import java.io.*;
import java.util.*;

public class ExceptionsTest {
    interface Dao { int eliminar(int id) throws SQLException; }
    private static final List<String> mensajes = new ArrayList<>();
    private static void mostrarError(String mensaje) { mensajes.add(mensaje); }
    private static void eliminar(Dao dao, int id) {
        // snippet:dao-boundary:start
        try {
            int filas = dao.eliminar(id);

            if (filas == 0) {
                mostrarError("No se encontró el registro.");
            }
        } catch (SQLException e) {
            mostrarError("No se pudo eliminar.");
        }
        // snippet:dao-boundary:end
    }
    public static void main(String[] args) throws IOException {
        eliminar(id -> 0, 1);
        eliminar(id -> 1, 1);
        eliminar(id -> { throw new SQLException("fixture"); }, 1);
        if (!mensajes.equals(List.of("No se encontró el registro.", "No se pudo eliminar."))) throw new AssertionError("DAO boundary");
        class Recurso implements AutoCloseable {
            boolean cerrado;
            @Override public void close() { cerrado = true; }
        }
        Recurso recurso = new Recurso();
        try (recurso) { throw new IOException("fixture"); }
        catch (IOException expected) { if (!recurso.cerrado) throw new AssertionError("resource not closed"); }
        System.out.println("Exceptions PASS");
    }
}
