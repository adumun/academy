import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class SentenciasDemo {

    // snippet:find:start
    public Optional<Libro> buscarPorId(Connection con, int id)
            throws SQLException {

        String sql = "SELECT id, titulo FROM libro WHERE id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Libro libro = new Libro(
                            rs.getInt("id"),
                            rs.getString("titulo")
                    );
                    return Optional.of(libro);
                }
            }
        }

        return Optional.empty();
    }
    // snippet:find:end

    // snippet:update:start
    public int actualizar(Connection con, Libro libro) throws SQLException {
        String sql = "UPDATE libro SET titulo = ? WHERE id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, libro.getTitulo());
            ps.setInt(2, libro.getId());

            return ps.executeUpdate();
        }
    }
    // snippet:update:end

    public int guardarFecha(Connection con, LocalDate fechaApertura) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(
                "INSERT INTO detalle_libro (fecha_apertura) VALUES (?)")) {
            // snippet:date:start
            if (fechaApertura == null) {
                ps.setNull(1, java.sql.Types.DATE);
            } else {
                ps.setDate(1, java.sql.Date.valueOf(fechaApertura));
            }
            // snippet:date:end

            return ps.executeUpdate();
        }
    }

    public void insertarConClave(Connection con, String titulo) throws SQLException {
        // snippet:keys:start
        String sql = "INSERT INTO libro (titulo) VALUES (?)";

        try (PreparedStatement ps = con.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, titulo);
            int filas = ps.executeUpdate();

            if (filas != 1) {
                throw new SQLException("Inserción no confirmada");
            }

            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (!claves.next()) {
                    throw new SQLException("Clave no disponible");
                }

                int idGenerado = claves.getInt(1);
                System.out.println(idGenerado);
            }
        }
        // snippet:keys:end

    }

    public void leerEjemplo(Connection conexion) throws SQLException {
        // snippet:read:start
        String consulta = "SELECT titulo FROM libro WHERE id = ?";
        try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
            sentencia.setInt(1, 7);
            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    System.out.println(resultado.getString("titulo"));
                } else {
                    System.out.println("Sin coincidencias");
                }
            }
        }
        // snippet:read:end

    }
}
