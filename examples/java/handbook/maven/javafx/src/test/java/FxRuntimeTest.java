import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;

class FxRuntimeTest {
    @BeforeAll static void iniciar() throws Exception {
        var ready = new CountDownLatch(1);
        Platform.startup(() -> {
            Platform.setImplicitExit(false);
            ready.countDown();
        });
        assertTrue(ready.await(15, TimeUnit.SECONDS), "Toolkit startup");
    }

    @AfterAll static void detener() { Platform.exit(); }

    interface Accion { void ejecutar() throws Exception; }

    static void fx(Accion accion) throws Exception {
        var task = new FutureTask<Void>(() -> { accion.ejecutar(); return null; });
        Platform.runLater(task);
        try { task.get(20, TimeUnit.SECONDS); }
        catch (ExecutionException e) {
            if (e.getCause() instanceof Error error) throw error;
            if (e.getCause() instanceof Exception exception) throw exception;
            throw e;
        }
    }

    static String salida(Accion accion) throws Exception {
        var original = System.out;
        var buffer = new ByteArrayOutputStream();
        try (var capture = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            System.setOut(capture);
            accion.ejecutar();
        } finally {
            System.setOut(original);
        }
        return buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
    }

    @Test void ventanaMinima() throws Exception {
        fx(() -> {
            Stage stage = new Stage();
            try {
                new app.Main().start(stage);
                assertTrue(stage.isShowing());
                assertEquals("Primera ventana", stage.getTitle());
                assertEquals("Hola, JavaFX", ((Label)((VBox)stage.getScene().getRoot()).getChildren().get(0)).getText());
            } finally { stage.close(); }
        });
    }

    @Test void eventosDemo() throws Exception {
        fx(() -> {
            Stage stage = new Stage();
            try {
                new EventosDemo().start(stage);
                var children = ((VBox)stage.getScene().getRoot()).getChildren();
                var entrada = (TextField)children.get(0);
                var boton = (Button)children.get(1);
                var mensaje = (Label)children.get(2);
                assertTrue(boton.isDisabled());
                entrada.setText(" ");
                assertTrue(boton.isDisabled());
                entrada.setText("Java");
                assertFalse(boton.isDisabled());
                boton.fire();
                assertEquals("Aceptado: Java", mensaje.getText());
                entrada.clear();
                assertTrue(boton.isDisabled());
            } finally { stage.close(); }
        });
    }

    @Test void accionYListeners() throws Exception {
        fx(() -> {
            var demo = new EventosFragmentos();
            demo.crearBoton().fire();
            assertEquals(1, demo.guardados);
            demo.escucharTitulo();
            demo.txtTitulo.setText("Java");
            assertFalse(demo.btnGuardar.isDisabled());
            demo.txtTitulo.setText(" ");
            assertTrue(demo.btnGuardar.isDisabled());
            demo.escucharSeleccion();
            var libro = new Libro(1, "SQL");
            demo.tablaLibros.getItems().add(libro);
            demo.tablaLibros.getSelectionModel().select(libro);
            assertEquals("SQL", demo.txtTitulo.getText());
            demo.tablaLibros.getSelectionModel().clearSelection();
            assertEquals("", demo.txtTitulo.getText());
        });
    }

    @Test void listaObservable() throws Exception {
        fx(() -> {
            assertEquals("[Java]\n", salida(() -> ListaObservableDemo.main(new String[0])));
            assertEquals("[B, C]\n[B, C]\n", salida(ListaFuente::alias));
            var a = new Libro(1, "Java");
            var b = new Libro(2, "SQL");
            var datos = new ArrayList<>(List.of(a));
            var modelo = new ListaFuente(() -> datos);
            var referencia = modelo.tablaLibros.getItems();
            modelo.refrescar();
            assertEquals(List.of(a), referencia);
            datos.add(b);
            modelo.refrescar();
            assertSame(referencia, modelo.tablaLibros.getItems());
            assertEquals(List.of(a,b), referencia);
        });
    }

    @Test void bindings() throws Exception {
        fx(() -> {
            assertEquals("Java\nJavaFX\nSQL\n", salida(() -> BindingDemo.main(new String[0])));
            assertEquals("10\n15\n", salida(BindingFragmentos::total));
            var form = new BindingFragmentos();
            form.vincular();
            assertTrue(form.btnGuardar.isDisabled());
            form.txtTitulo.setText("Java");
            assertFalse(form.btnGuardar.isDisabled());
            form.txtTitulo.clear();
            assertTrue(form.btnGuardar.isDisabled());
            assertThrows(RuntimeException.class, () -> form.btnGuardar.setDisable(false));
        });
    }

    @Test void filtroOrdenYFuente() throws Exception {
        fx(() -> {
            Stage stage = new Stage();
            try {
                new catalogo.Main().start(stage);
                var children = ((VBox)stage.getScene().getRoot()).getChildren();
                var buscador = (TextField)children.get(0);
                @SuppressWarnings("unchecked")
                var tabla = (TableView<catalogo.Main.Libro>)children.get(1);
                assertEquals(3, tabla.getItems().size());
                buscador.setText(" JAVA ");
                assertEquals(List.of("Java","JavaFX"), tabla.getItems().stream().map(catalogo.Main.Libro::titulo).toList());
                var columna = tabla.getColumns().get(1);
                columna.setSortType(TableColumn.SortType.DESCENDING);
                tabla.getSortOrder().setAll(List.of(columna));
                tabla.sort();
                assertEquals(List.of("JavaFX","Java"), tabla.getItems().stream().map(catalogo.Main.Libro::titulo).toList());
                buscador.setText("inexistente");
                assertTrue(tabla.getItems().isEmpty());
                buscador.clear();
                assertEquals(3, tabla.getItems().size());
            } finally { stage.close(); }
        });
    }

    @Test void columnaObservaPropiedad() throws Exception {
        fx(() -> {
            var libro = new LibroObservable("Java");
            var modelo = new ColumnaObservable();
            var tabla = new TableView<LibroObservable>();
            tabla.getColumns().add(modelo.colTitulo);
            tabla.getItems().add(libro);
            modelo.configurar();
            var valor = modelo.colTitulo.getCellObservableValue(0);
            assertSame(libro.tituloProperty(), valor);
            libro.setTitulo("SQL");
            assertEquals("SQL", valor.getValue());
        });
    }

    @Test void cargaFxmlEInyeccion() throws Exception {
        fx(() -> {
            Stage stage = new Stage();
            try {
                new VentanaLibro().mostrar(stage);
                var children = ((VBox)stage.getScene().getRoot()).getChildren();
                var titulo = (TextField)children.get(0);
                var boton = (Button)children.get(1);
                var resultado = (Label)children.get(2);
                assertEquals("Esperando entrada", resultado.getText());
                titulo.setText("Java");
                boton.fire();
                assertEquals("Java", resultado.getText());
                assertTrue(stage.isShowing());
            } finally { stage.close(); }
        });
    }

    @Test void sceneBuilderFxml() throws Exception {
        fx(() -> {
            var loader = new FXMLLoader(getClass().getResource("/view/form.fxml"));
            VBox vista = loader.load();
            assertInstanceOf(app.FormController.class, loader.getController());
            ((TextField)vista.getChildren().get(0)).setText("Java");
            assertEquals("Java\n", salida(() -> ((Button)vista.getChildren().get(1)).fire()));
        });
    }

    @Test void navegarYVolver() throws Exception {
        fx(() -> {
            Stage stage = new Stage();
            try {
                var servicio = new navegacion.Servicio("Compartido");
                var nav = new navegacion.NavegacionDemo(stage, servicio);
                nav.volverAlListado();
                Scene listado = stage.getScene();
                nav.abrirDetalle();
                assertNotSame(listado, stage.getScene());
                var children = ((VBox)stage.getScene().getRoot()).getChildren();
                assertEquals("Compartido", ((Label)children.get(0)).getText());
                ((Button)children.get(1)).fire();
                assertEquals("Listado: Compartido", ((Label)stage.getScene().getRoot()).getText());
            } finally { stage.close(); }
        });
    }
}
