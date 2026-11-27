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

    @Test void selectorYFecha() throws Exception {
        fx(() -> {
            var libro = new Libro(8, "Java");
            var demo = new ControlesDemo(() -> List.of(libro));
            assertEquals("", salida(demo::cargarSelector));
            assertFalse(demo.colLibros.isEditable());
            assertEquals(List.of(libro), demo.colLibros.getItems());
            assertEquals("Java", demo.colLibros.getConverter().toString(libro));
            assertEquals("", demo.colLibros.getConverter().toString(null));
            assertThrows(UnsupportedOperationException.class,
                    () -> demo.colLibros.getConverter().fromString("otro"));
            demo.colLibros.getSelectionModel().select(libro);
            assertSame(libro, demo.colLibros.getValue());
            demo.confirmarFecha();
            assertFalse(demo.confirmadoValido);
            assertEquals("Completa la fecha y confirma.", demo.mensaje.getText());
            demo.fechaVencimiento.setValue(java.time.LocalDate.of(2026, 11, 27));
            demo.confirmarFecha();
            assertFalse(demo.confirmadoValido);
            demo.chkConfirmado.setSelected(true);
            demo.confirmarFecha();
            assertTrue(demo.confirmadoValido);
        });
    }

    @Test void cantidadControl() throws Exception {
        fx(() -> {
            var demo = new ControlesDemo(List::of);
            for (String entrada : List.of("", " ", "abc", "2147483648")) {
                demo.cantidadField.setText(entrada);
                demo.convertirCantidad();
                assertEquals("Ingresa un entero válido", demo.mensaje.getText());
            }
            for (String entrada : List.of("0", "-1")) {
                demo.cantidadField.setText(entrada);
                demo.convertirCantidad();
                assertEquals("La cantidad debe ser positiva", demo.mensaje.getText());
            }
            demo.cantidadField.setText(" 12 ");
            demo.convertirCantidad();
            assertEquals("Cantidad válida: 12", demo.mensaje.getText());
        });
    }

    @Test void tablaCargaYColumnas() throws Exception {
        fx(() -> {
            var a = new Libro(8, "Java");
            var b = new Libro(3, "SQL");
            var filas = new ArrayList<>(List.of(a,b));
            var falla = new java.util.concurrent.atomic.AtomicBoolean();
            var demo = TablaLibrosDemo.abrir(() -> {
                if (falla.get()) throw new java.sql.SQLException("fixture");
                return filas;
            });
            var lista = demo.tabla().getItems();
            assertEquals(List.of(a,b), lista);
            assertEquals(8, demo.id().getCellData(0));
            assertEquals("SQL", demo.titulo().getCellData(1));
            demo.alternativa();
            demo.columnas();
            assertEquals(8, demo.id().getCellData(0));
            assertEquals("Java", demo.titulo().getCellData(0));
            assertEquals(80.0, demo.id().getPrefWidth());
            assertEquals(280.0, demo.titulo().getPrefWidth());
            filas.clear();
            demo.recargar();
            assertSame(lista, demo.tabla().getItems());
            assertTrue(lista.isEmpty());
            filas.add(b);
            demo.recargar();
            assertEquals(List.of(b), lista);
            falla.set(true);
            filas.clear();
            demo.recargar();
            assertEquals(List.of(b), lista);
            assertEquals("No se pudo cargar el listado.", demo.error);
        });
    }

    @Test void tablaSeleccionYOrden() throws Exception {
        fx(() -> {
            var a = new Libro(8, "SQL");
            var b = new Libro(3, "Java");
            var demo = TablaLibrosDemo.abrir(() -> List.of(a,b));
            demo.escuchar();
            demo.seleccionar();
            assertEquals(-1, demo.idSeleccionado);
            assertEquals("Selecciona un libro.", demo.error);
            demo.titulo().setSortType(TableColumn.SortType.ASCENDING);
            demo.tabla().getSortOrder().setAll(List.of(demo.titulo()));
            demo.tabla().sort();
            assertSame(b, demo.tabla().getItems().get(0));
            demo.tabla().getSelectionModel().select(0);
            demo.seleccionar();
            assertEquals(3, demo.idSeleccionado);
            assertEquals("Java", demo.txtTitulo.getText());
            demo.capturarSeleccion();
            demo.tabla().getSelectionModel().selectIndices(0,1);
            demo.capturarSeleccion();
            assertEquals(Set.of(a,b), new HashSet<>(demo.copiaSeleccion));
            demo.tabla().getSelectionModel().clearSelection();
            assertEquals("", demo.txtTitulo.getText());
            assertEquals(2, demo.copiaSeleccion.size());
        });
    }

    @Test void tablaCeldaPersonalizada() throws Exception {
        fx(() -> {
            var libro = new Libro(8, "Java");
            var demo = TablaLibrosDemo.abrir(() -> List.of(libro));
            demo.personalizar();
            Stage stage = new Stage();
            try {
                stage.setScene(new Scene(demo.tabla(), 480, 320));
                stage.show();
                demo.tabla().applyCss();
                demo.tabla().layout();
                var celda = demo.tabla().lookupAll(".table-cell").stream()
                        .filter(n -> n instanceof TableCell<?,?> c && "JAVA".equals(c.getText()))
                        .map(n -> (TableCell<?,?>) n).findFirst().orElseThrow();
                assertEquals("Java", libro.getTitulo());
                celda.updateIndex(-1);
                assertNull(celda.getText());
                assertNull(celda.getGraphic());
            } finally { stage.close(); }
        });
    }

    static final class DaoControlado implements ValidacionDemo.Dao {
        int intentos, recargas, filas = 1;
        boolean fallaInsercion, fallaRecarga;
        Libro ultimo;
        @Override public int insertar(Libro libro) throws java.sql.SQLException {
            intentos++;
            ultimo = libro;
            if (fallaInsercion) throw new java.sql.SQLException("fixture insert");
            return filas;
        }
        @Override public void cargar() throws java.sql.SQLException {
            recargas++;
            if (fallaRecarga) throw new java.sql.SQLException("fixture reload");
        }
    }

    @Test void tituloYNumeroInvalidos() throws Exception {
        fx(() -> {
            var dao = new DaoControlado();
            var errores = new ArrayList<String>();
            var demo = new ValidacionDemo(dao, errores::add);
            assertFalse(demo.comprobarTitulo(null));
            for (String titulo : List.of("", "   ", "a".repeat(121))) {
                demo.txtTitulo.setText(titulo);
                demo.ejecutarGuardado();
            }
            assertEquals(0, dao.intentos);
            assertEquals(0, dao.recargas);
            assertTrue(demo.comprobarTitulo(" " + "a".repeat(120) + " "));
            assertTrue(errores.contains("El título es obligatorio."));
            assertTrue(errores.contains("El título no puede superar 120 caracteres."));
            for (String entrada : List.of("", "abc", "2147483648", "0", "-1")) {
                demo.txtCantidad.setText(entrada);
                demo.validarCantidad();
                assertFalse(demo.cantidadValida);
            }
            demo.txtCantidad.setText(" 21 ");
            demo.validarCantidad();
            assertTrue(demo.cantidadValida);
        });
    }

    @Test void guardadoYRefrescoSeparados() throws Exception {
        fx(() -> {
            var dao = new DaoControlado();
            var errores = new ArrayList<String>();
            var demo = new ValidacionDemo(dao, errores::add);
            demo.txtTitulo.setText("  Java  ");
            dao.fallaInsercion = true;
            demo.ejecutarGuardado();
            assertEquals("  Java  ", demo.txtTitulo.getText());
            assertEquals(0, dao.recargas);
            assertEquals("No se pudo confirmar el guardado del libro.", errores.getLast());
            dao.fallaInsercion = false;
            dao.filas = 0;
            demo.ejecutarGuardado();
            assertEquals("  Java  ", demo.txtTitulo.getText());
            assertEquals(0, dao.recargas);
            assertEquals("No se confirmó la inserción.", errores.getLast());
            dao.filas = 1;
            dao.fallaRecarga = true;
            demo.ejecutarGuardado();
            assertEquals("", demo.txtTitulo.getText());
            assertEquals(3, dao.intentos);
            assertEquals(1, dao.recargas);
            assertEquals(0, dao.ultimo.getId());
            assertEquals("Java", dao.ultimo.getTitulo());
            assertEquals("Libro guardado; no se pudo actualizar el listado.", errores.getLast());
            dao.fallaRecarga = false;
            errores.clear();
            demo.txtTitulo.setText("SQL");
            demo.ejecutarGuardado();
            assertEquals("", demo.txtTitulo.getText());
            assertEquals(4, dao.intentos);
            assertEquals(2, dao.recargas);
            assertTrue(errores.isEmpty());
        });
    }

    static void responderDialogo(String texto, ButtonType respuesta, Accion accion) throws Exception {
        var fallo = new java.util.concurrent.atomic.AtomicReference<Throwable>();
        var observado = new java.util.concurrent.atomic.AtomicBoolean();
        Platform.runLater(() -> {
            var ventanas = new ArrayList<>(javafx.stage.Window.getWindows());
            DialogPane panel = null;
            try {
                panel = ventanas.stream().filter(javafx.stage.Window::isShowing)
                        .map(javafx.stage.Window::getScene).filter(Objects::nonNull)
                        .map(Scene::getRoot).filter(DialogPane.class::isInstance)
                        .map(DialogPane.class::cast).findFirst().orElseThrow();
                assertEquals(texto, panel.getContentText());
                var boton = (Button) panel.lookupButton(respuesta);
                assertNotNull(boton);
                observado.set(true);
                boton.fire();
            } catch (Throwable e) {
                fallo.set(e);
                if (panel != null) panel.getScene().getWindow().hide();
                else ventanas.stream().filter(javafx.stage.Window::isShowing)
                        .forEach(javafx.stage.Window::hide);
            }
        });
        accion.ejecutar();
        if (fallo.get() != null) throw new AssertionError("Dialog fixture", fallo.get());
        assertTrue(observado.get(), "No se observó diálogo");
    }

    @Test void confirmacionYAlertaReales() throws Exception {
        fx(() -> {
            var demo = new AlertasDemo();
            var escrituras = new java.util.concurrent.atomic.AtomicInteger();
            String pregunta = "¿Eliminar el libro seleccionado?";
            responderDialogo(pregunta, ButtonType.CANCEL,
                    () -> demo.confirmarEliminacion(escrituras::incrementAndGet));
            assertEquals(0, escrituras.get());
            responderDialogo(pregunta, ButtonType.OK,
                    () -> demo.confirmarEliminacion(escrituras::incrementAndGet));
            assertEquals(1, escrituras.get());
            responderDialogo("Error de prueba", ButtonType.OK,
                    () -> demo.informar("Error de prueba"));
        });
    }

    @Test void xmlYFxmlValidos() throws Exception {
        fx(() -> {
            var factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            try (var in = getClass().getResourceAsStream("/view/libro.xml")) {
                assertNotNull(in);
                var doc = factory.newDocumentBuilder().parse(in);
                assertEquals("libro", doc.getDocumentElement().getTagName());
                assertEquals("1", doc.getDocumentElement().getAttribute("id"));
                assertEquals("Java", doc.getElementsByTagName("titulo").item(0).getTextContent());
            }
            var loader = new FXMLLoader(getClass().getResource("/view/guardar.fxml"));
            VBox root = loader.load();
            var boton = (Button)root.getChildren().get(0);
            assertEquals("Guardar", boton.getText());
            assertSame(boton, loader.getNamespace().get("btnGuardar"));
        });
    }

    @Test void xmlMalformadoYFxmlInvalido() throws Exception {
        fx(() -> {
            var factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            var parser = factory.newDocumentBuilder();
            parser.setErrorHandler(new org.xml.sax.helpers.DefaultHandler());
            assertThrows(org.xml.sax.SAXException.class, () -> parser.parse(
                    new ByteArrayInputStream("<libro><titulo></libro>".getBytes(StandardCharsets.UTF_8))));
            String fxml = "<?import javafx.scene.control.Button?>\n<Button propiedadInexistente=\"x\"/>";
            assertThrows(javafx.fxml.LoadException.class, () -> new FXMLLoader().load(
                    new ByteArrayInputStream(fxml.getBytes(StandardCharsets.UTF_8))));
        });
    }
}
