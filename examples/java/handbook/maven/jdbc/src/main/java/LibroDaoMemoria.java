import java.util.ArrayList;
import java.util.List;

public class LibroDaoMemoria implements LibroDao {

    private final List<Libro> libros = new ArrayList<>();

    @Override
    public List<Libro> listarTodos() {
        return new ArrayList<>(libros);
    }

    @Override
    public int insertar(Libro libro) {
        libros.add(libro);
        return 1;
    }

    @Override
    public int actualizar(Libro libro) {
        for (int i = 0; i < libros.size(); i++) {
            if (libros.get(i).getId() == libro.getId()) {
                libros.set(i, libro);
                return 1;
            }
        }

        return 0;
    }

    @Override
    public int eliminar(int id) {
        int anterior = libros.size();
        libros.removeIf(libro -> libro.getId() == id);
        return anterior - libros.size();
    }
}
