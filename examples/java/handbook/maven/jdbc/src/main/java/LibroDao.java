import java.sql.SQLException;
import java.util.List;

public interface LibroDao {
    List<Libro> listarTodos() throws SQLException;
    int insertar(Libro libro) throws SQLException;
    int actualizar(Libro libro) throws SQLException;
    int eliminar(int id) throws SQLException;
}
