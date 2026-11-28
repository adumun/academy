import database.DatabaseConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class JdbcCrudDaoTest {
    private final LibroDao dao = new LibroDaoMySql();
    private final String prefijo = "crud-" + UUID.randomUUID() + "-";

    private Libro entrada(String sufijo) { return new Libro(0, prefijo + sufijo); }

    private List<Libro> consultarSql() throws SQLException {
        try (var con = DatabaseConnection.getConnection();
             var ps = con.prepareStatement("SELECT id, titulo FROM libro WHERE titulo LIKE ? ORDER BY id")) {
            ps.setString(1, prefijo + "%");
            try (var rs = ps.executeQuery()) {
                var libros = new java.util.ArrayList<Libro>();
                while (rs.next()) libros.add(new Libro(rs.getInt(1), rs.getString(2)));
                return libros;
            }
        }
    }

    @AfterEach
    void limpiar() throws SQLException {
        // Solo filas pertenecientes a este caso, en el esquema efímero.
        try (var con = DatabaseConnection.getConnection();
             var ps = con.prepareStatement("DELETE FROM libro WHERE titulo LIKE ?")) {
            ps.setString(1, prefijo + "%");
            ps.executeUpdate();
        }
    }

    @Test
    void listarVacio() throws SQLException {
        assertTrue(dao.listarTodos().isEmpty());
        assertTrue(consultarSql().isEmpty());
    }

    @Test
    void insertarYConfirmar() throws SQLException {
        Libro entrada = entrada("alta");
        assertEquals(1, dao.insertar(entrada));
        assertEquals(0, entrada.getId(), "El contrato retorna conteo, no modifica el modelo");
        var filas = consultarSql();
        assertEquals(1, filas.size());
        assertTrue(filas.getFirst().getId() > 0);
        assertEquals(entrada.getTitulo(), filas.getFirst().getTitulo());
        var otraInstancia = new LibroDaoMySql().listarTodos();
        assertEquals(filas.getFirst().getId(), otraInstancia.getFirst().getId());
    }

    @Test
    void identidadYOrdenConTitulosRepetidos() throws SQLException {
        assertEquals(1, dao.insertar(entrada("repetido")));
        assertEquals(1, dao.insertar(entrada("repetido")));
        var filas = consultarSql();
        assertEquals(2, filas.size());
        assertTrue(filas.get(0).getId() < filas.get(1).getId());
        assertEquals(filas.stream().map(Libro::getId).toList(),
                dao.listarTodos().stream().map(Libro::getId).toList());
        assertNotSame(filas.get(0), filas.get(1));
        assertEquals(1, dao.eliminar(filas.getFirst().getId()));
        assertEquals(filas.getLast().getId(), consultarSql().getFirst().getId());
    }

    @Test
    void actualizarExistenteYAusente() throws SQLException {
        assertEquals(1, dao.insertar(entrada("antes")));
        int id = consultarSql().getFirst().getId();
        assertEquals(1, dao.insertar(entrada("intacto")));
        assertEquals(1, dao.actualizar(new Libro(id, prefijo + "después")));
        assertEquals(List.of(prefijo + "después", prefijo + "intacto"),
                consultarSql().stream().map(Libro::getTitulo).toList());
        assertEquals(0, dao.actualizar(new Libro(-1, prefijo + "ausente")));
        assertEquals(2, consultarSql().size());
    }

    @Test
    void eliminarExistenteYAusente() throws SQLException {
        assertEquals(1, dao.insertar(entrada("borrar")));
        int id = consultarSql().getFirst().getId();
        assertEquals(1, dao.eliminar(id));
        assertTrue(consultarSql().isEmpty());
        assertEquals(0, dao.eliminar(id));
        assertTrue(new LibroDaoMySql().listarTodos().isEmpty());
    }

    @Test
    void falloNoEsAusencia() throws SQLException {
        assertEquals(1, dao.insertar(entrada("intacto")));
        int id = consultarSql().getFirst().getId();
        var error = assertThrows(SQLException.class, () -> dao.insertar(new Libro(0, null)));
        assertTrue(error.getSQLState().startsWith("23"), error.getSQLState());
        assertThrows(SQLException.class, () -> dao.actualizar(new Libro(id, null)));
        assertEquals(prefijo + "intacto", consultarSql().getFirst().getTitulo());
        assertEquals(1, consultarSql().size());
    }

    @Test
    void contratoMemoriaParcial() throws SQLException {
        LibroDao memoria = new LibroDaoMemoria();
        assertTrue(memoria.listarTodos().isEmpty());
        assertEquals(1, memoria.insertar(new Libro(10, "Uno")));
        assertEquals(1, memoria.insertar(new Libro(20, "Dos")));
        memoria.listarTodos().clear();
        assertEquals(2, memoria.listarTodos().size(), "La lista retornada no es la lista interna");
        assertEquals(1, memoria.actualizar(new Libro(10, "Nuevo")));
        assertEquals("Nuevo", memoria.listarTodos().getFirst().getTitulo());
        assertEquals(0, memoria.actualizar(new Libro(30, "Ausente")));
        assertEquals(1, memoria.eliminar(10));
        assertEquals(0, memoria.eliminar(10));
        assertEquals(20, memoria.listarTodos().getFirst().getId());
    }

    private String proceso(String... args) throws Exception {
        var comando = new java.util.ArrayList<String>();
        comando.add(Path.of(System.getProperty("java.home"), "bin", "java").toString());
        comando.add("-cp");
        comando.add(System.getProperty("surefire.test.class.path", System.getProperty("java.class.path")));
        comando.add("DaoProcesoDemo");
        comando.addAll(List.of(args));
        var proceso = new ProcessBuilder(comando).redirectErrorStream(true).start();
        try {
            assertTrue(proceso.waitFor(25, TimeUnit.SECONDS), "El proceso no terminó");
            String salida = new String(proceso.getInputStream().readAllBytes(), StandardCharsets.UTF_8).strip();
            assertEquals(0, proceso.exitValue(), salida);
            return salida;
        } finally {
            if (proceso.isAlive()) proceso.destroyForcibly();
        }
    }

    @Test
    void persistenciaEntreProcesos() throws Exception {
        String titulo = prefijo + "persistente";
        assertEquals("1", proceso("insertar", titulo));
        var filas = consultarSql();
        assertEquals(1, filas.size());
        int id = filas.getFirst().getId();
        assertEquals(titulo, filas.getFirst().getTitulo());
        assertEquals(id + "\t" + titulo, proceso("listar"));
        assertEquals(1, dao.actualizar(new Libro(id, prefijo + "actualizado")));
        assertEquals(id + "\t" + prefijo + "actualizado", proceso("listar"));
        assertEquals(1, dao.eliminar(id));
        assertTrue(consultarSql().isEmpty());
        assertEquals("", proceso("listar"));
    }
}
