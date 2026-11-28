import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MapeoDemo {

    // snippet:map:start
    public List<Libro> mapear(ResultSet rs) throws SQLException {
        List<Libro> libros = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            String titulo = rs.getString("titulo");

            Libro libro = new Libro(id, titulo);
            libros.add(libro);
        }

        return libros;
    }
    // snippet:map:end

    // snippet:separate:start
    private Libro mapearFila(ResultSet rs) throws SQLException {
        return new Libro(
                rs.getInt("id"),
                rs.getString("titulo")
        );
    }

    private List<Libro> mapearTodas(ResultSet rs)
            throws SQLException {

        List<Libro> libros = new ArrayList<>();

        while (rs.next()) {
            libros.add(mapearFila(rs));
        }

        return libros;
    }
    // snippet:separate:end

    public List<Libro> listarMapeoSeparado(Connection con) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement("SELECT id, titulo FROM libro ORDER BY id");
             ResultSet rs = ps.executeQuery()) {
            return mapearTodas(rs);
        }
    }

    public record Detalle(Integer cantidad, LocalDate fecha) {}

    public Detalle leerDetalle(ResultSet rs) throws SQLException {
        // snippet:nullable:start
        int cantidad = rs.getInt("cantidad");
        Integer cantidadOpcional = rs.wasNull() ? null : cantidad;

        java.sql.Date fechaSql = rs.getDate("fecha_apertura");
        LocalDate fecha = fechaSql == null ? null : fechaSql.toLocalDate();
        // snippet:nullable:end

        return new Detalle(cantidadOpcional, fecha);
    }
}
