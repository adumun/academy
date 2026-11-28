import database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDaoMySql implements LibroDao {

    @Override
    // snippet:insert:start
    public int insertar(Libro libro) throws SQLException {
        String sql = "INSERT INTO libro (titulo) VALUES (?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, libro.getTitulo());
            return ps.executeUpdate();
        }
    }
    // snippet:insert:end

    @Override
    // snippet:list:start
    public List<Libro> listarTodos() throws SQLException {
        String sql = "SELECT id, titulo FROM libro ORDER BY id";
        List<Libro> libros = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Libro libro = new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo")
                );
                libros.add(libro);
            }
        }

        return libros;
    }
    // snippet:list:end

    @Override
    // snippet:update:start
    public int actualizar(Libro libro) throws SQLException {
        String sql = "UPDATE libro SET titulo = ? WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, libro.getTitulo());
            ps.setInt(2, libro.getId());
            return ps.executeUpdate();
        }
    }
    // snippet:update:end

    @Override
    // snippet:delete:start
    public int eliminar(int id) throws SQLException {
        String sql = "DELETE FROM libro WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate();
        }
    }
    // snippet:delete:end

}
