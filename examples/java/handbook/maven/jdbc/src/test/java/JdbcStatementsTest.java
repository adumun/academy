import database.DatabaseConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JdbcStatementsTest {
    private Connection con;
    private final SentenciasDemo sentencias = new SentenciasDemo();
    private final MapeoDemo mapeo = new MapeoDemo();

    @BeforeEach
    void abrir() throws SQLException {
        con = DatabaseConnection.getConnection();
        con.setAutoCommit(false);
    }

    @AfterEach
    void revertir() throws SQLException {
        if (con != null) {
            try (con) {
                con.rollback();
            }
        }
    }

    private int insertar(String titulo) throws SQLException {
        try (var ps = con.prepareStatement("INSERT INTO libro (titulo) VALUES (?)",
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, titulo);
            assertEquals(1, ps.executeUpdate());
            try (var keys = ps.getGeneratedKeys()) {
                assertTrue(keys.next());
                int id = keys.getInt(1);
                assertTrue(id > 0);
                assertFalse(keys.next());
                return id;
            }
        }
    }

    private List<Libro> listar() throws SQLException {
        try (var ps = con.prepareStatement("SELECT id, titulo FROM libro ORDER BY id");
             var rs = ps.executeQuery()) {
            return mapeo.mapear(rs);
        }
    }

    @FunctionalInterface
    interface Accion { void ejecutar() throws Exception; }

    private String capturar(Accion accion) throws Exception {
        var original = System.out;
        var bytes = new ByteArrayOutputStream();
        try (var captura = new PrintStream(bytes, true, StandardCharsets.UTF_8)) {
            System.setOut(captura);
            accion.ejecutar();
        } finally {
            System.setOut(original);
        }
        return bytes.toString(StandardCharsets.UTF_8).strip();
    }

    @Test
    void buscarPresenciaAusencia() throws SQLException {
        int id = insertar("Java");
        var encontrado = sentencias.buscarPorId(con, id).orElseThrow();
        assertEquals(id, encontrado.getId());
        assertEquals("Java", encontrado.getTitulo());
        assertTrue(sentencias.buscarPorId(con, -1).isEmpty());
        assertFalse(con.isClosed());
        assertTrue(con.isValid(3));
    }

    @Test
    void actualizarConteo() throws SQLException {
        int id = insertar("Inicial");
        assertEquals(1, sentencias.actualizar(con, new Libro(id, "Nuevo")));
        assertEquals("Nuevo", sentencias.buscarPorId(con, id).orElseThrow().getTitulo());
        assertEquals(0, sentencias.actualizar(con, new Libro(-1, "Ausente")));
        assertFalse(con.isClosed());
    }

    @Test
    void tituloComoDato() throws SQLException {
        int id = insertar("Inicial");
        int otro = insertar("Intacto");
        String valor = "O'Connor";
        assertEquals(1, sentencias.actualizar(con, new Libro(id, valor)));
        assertEquals(valor, sentencias.buscarPorId(con, id).orElseThrow().getTitulo());
        assertEquals("Intacto", sentencias.buscarPorId(con, otro).orElseThrow().getTitulo());
        assertEquals(2, listar().size());
    }

    @Test
    void claveGenerada() throws Exception {
        String salida = capturar(() -> sentencias.insertarConClave(con, "Clave"));
        int id = Integer.parseInt(salida);
        assertTrue(id > 0);
        assertEquals("Clave", sentencias.buscarPorId(con, id).orElseThrow().getTitulo());
        assertFalse(con.isClosed());
    }

    @Test
    void fechaNulaYValor() throws SQLException {
        LocalDate fecha = LocalDate.of(2026, 2, 28);
        assertEquals(1, sentencias.guardarFecha(con, null));
        assertEquals(1, sentencias.guardarFecha(con, fecha));
        try (var ps = con.prepareStatement("SELECT cantidad, fecha_apertura FROM detalle_libro ORDER BY id");
             var rs = ps.executeQuery()) {
            assertTrue(rs.next());
            assertEquals(new MapeoDemo.Detalle(null, null), mapeo.leerDetalle(rs));
            assertTrue(rs.next());
            assertEquals(new MapeoDemo.Detalle(null, fecha), mapeo.leerDetalle(rs));
            assertFalse(rs.next());
        }
    }

    @Test
    void lecturaFragmento() throws Exception {
        // El fragmento publicado busca explícitamente el ID 7.
        assertEquals("Sin coincidencias", capturar(() -> sentencias.leerEjemplo(con)));
        try (var ps = con.prepareStatement("INSERT INTO libro (id, titulo) VALUES (?, ?)")) {
            ps.setInt(1, 7);
            ps.setString(2, "Encontrado");
            assertEquals(1, ps.executeUpdate());
        }
        assertEquals("Encontrado", capturar(() -> sentencias.leerEjemplo(con)));
        assertFalse(con.isClosed());
    }

    @Test
    void cursorCeroUnaVarias() throws SQLException {
        assertTrue(listar().isEmpty());
        int a = insertar("Java");
        var una = listar();
        assertEquals(1, una.size());
        assertEquals(a, una.getFirst().getId());
        int b = insertar("SQL");
        var varias = listar();
        assertEquals(List.of(a, b), varias.stream().map(Libro::getId).toList());
        assertEquals(List.of("Java", "SQL"), varias.stream().map(Libro::getTitulo).toList());
        assertNotSame(varias.get(0), varias.get(1));
    }

    @Test
    void mapeoSeparado() throws SQLException {
        assertTrue(mapeo.listarMapeoSeparado(con).isEmpty());
        int id = insertar("Uno");
        int otro = insertar("Dos");
        var libros = mapeo.listarMapeoSeparado(con);
        assertEquals(List.of(id, otro), libros.stream().map(Libro::getId).toList());
        assertEquals(List.of("Uno", "Dos"), libros.stream().map(Libro::getTitulo).toList());
        assertFalse(con.isClosed());
    }

    @Test
    void nullYConversiones() throws SQLException {
        try (var ps = con.prepareStatement(
                "INSERT INTO detalle_libro (cantidad, fecha_apertura, precio) VALUES (?, ?, ?)")) {
            ps.setNull(1, Types.INTEGER);
            ps.setNull(2, Types.DATE);
            ps.setNull(3, Types.DECIMAL);
            assertEquals(1, ps.executeUpdate());
            ps.setInt(1, 0);
            ps.setDate(2, java.sql.Date.valueOf("2026-11-28"));
            ps.setBigDecimal(3, new BigDecimal("12.30"));
            assertEquals(1, ps.executeUpdate());
        }
        try (var ps = con.prepareStatement(
                "SELECT cantidad, fecha_apertura, precio AS monto FROM detalle_libro ORDER BY id");
             var rs = ps.executeQuery()) {
            assertTrue(rs.next());
            assertEquals(0, rs.getInt("cantidad"));
            assertTrue(rs.wasNull());
            assertEquals(new MapeoDemo.Detalle(null, null), mapeo.leerDetalle(rs));
            assertNull(rs.getBigDecimal("monto"));
            assertTrue(rs.next());
            assertEquals(0, rs.getInt("cantidad"));
            assertFalse(rs.wasNull());
            assertEquals(new MapeoDemo.Detalle(0, LocalDate.of(2026, 11, 28)), mapeo.leerDetalle(rs));
            assertEquals(new BigDecimal("12.30"), rs.getBigDecimal("monto"));
            assertFalse(rs.next());
        }
    }

    @Test
    void parametrosInvalidos() throws SQLException {
        try (var ps = con.prepareStatement("INSERT INTO libro (titulo) VALUES (?)")) {
            assertThrows(SQLException.class, () -> ps.setString(0, "Incorrecto"));
        }
        try (var ps = con.prepareStatement("INSERT INTO libro (titulo) VALUES (?)")) {
            assertThrows(SQLException.class, ps::executeUpdate);
        }
        assertTrue(listar().isEmpty());
        assertTrue(con.isValid(3));
    }

    @Test
    void etiquetaInexistente() throws SQLException {
        insertar("Java");
        try (var ps = con.prepareStatement("SELECT titulo AS nombre_libro FROM libro");
             var rs = ps.executeQuery()) {
            assertTrue(rs.next());
            assertEquals("Java", rs.getString("nombre_libro"));
            assertThrows(SQLException.class, () -> rs.getString("columna_inexistente"));
        }
    }

    @Test
    void cursorEnMemoria() throws Exception {
        var salida = capturar(() -> CursorDemo.main(new String[0]));
        assertEquals(List.of("true", "0", "false", "false"), salida.lines().toList());
    }
}
