import java.time.LocalDate;
import java.sql.*;
import java.lang.reflect.Proxy;
import java.util.concurrent.atomic.AtomicReference;

public class DatesTest {
    private static LocalDate convertir(LocalDate fecha, PreparedStatement ps, ResultSet rs) throws SQLException {
        // snippet:sql-date:start
        java.sql.Date fechaSql = java.sql.Date.valueOf(fecha);
        ps.setDate(1, fechaSql);

        java.sql.Date recibida = rs.getDate("fecha");
        LocalDate resultado = recibida == null
                ? null
                : recibida.toLocalDate();
        // snippet:sql-date:end
        return resultado;
    }
    private static ResultSet fila(java.sql.Date valor) {
        return (ResultSet) Proxy.newProxyInstance(DatesTest.class.getClassLoader(), new Class<?>[]{ResultSet.class}, (obj, metodo, argumentos) -> {
            if (!metodo.getName().equals("getDate") || !argumentos[0].equals("fecha")) throw new AssertionError("unexpected JDBC call");
            return valor;
        });
    }
    public static void main(String[] args) throws SQLException {
        LocalDate original = LocalDate.of(2024, 2, 29);
        AtomicReference<java.sql.Date> escrito = new AtomicReference<>();
        PreparedStatement ps = (PreparedStatement) Proxy.newProxyInstance(DatesTest.class.getClassLoader(), new Class<?>[]{PreparedStatement.class}, (obj, metodo, argumentos) -> {
            if (!metodo.getName().equals("setDate") || !argumentos[0].equals(1)) throw new AssertionError("unexpected JDBC call");
            escrito.set((java.sql.Date) argumentos[1]);
            return null;
        });
        if (!original.equals(convertir(original, ps, fila(java.sql.Date.valueOf(original)))) || !original.equals(escrito.get().toLocalDate())) throw new AssertionError("SQL DATE conversion");
        if (convertir(original, ps, fila(null)) != null) throw new AssertionError("SQL NULL conversion");
        if (!original.plusDays(1).equals(LocalDate.of(2024, 3, 1))) throw new AssertionError("leap day");
        // snippet:invalid-date:start
        try {
            java.time.LocalDate fecha = java.time.LocalDate.parse("2026-02-30");
            System.out.println(fecha);
        } catch (java.time.format.DateTimeParseException exception) {
            System.out.println("Fecha invalida");
        }
        // snippet:invalid-date:end
        System.out.println("Dates PASS");
    }
}
