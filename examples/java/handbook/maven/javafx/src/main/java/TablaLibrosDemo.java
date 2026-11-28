import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import java.sql.SQLException;
import java.io.IOException;
import java.util.*;

public class TablaLibrosDemo {
    interface Dao { List<Libro> listarTodos() throws SQLException; }
    private final Dao dao;
    final TextField txtTitulo = new TextField();
    String error;
    int idSeleccionado = -1;
    List<Libro> copiaSeleccion = List.of();

    TablaLibrosDemo(Dao dao) { this.dao = dao; }

    static TablaLibrosDemo abrir(Dao dao) throws IOException {
        var demo = new TablaLibrosDemo(dao);
        var loader = new FXMLLoader(TablaLibrosDemo.class.getResource("/view/tabla.fxml"));
        loader.setControllerFactory(tipo -> {
            if (tipo != TablaLibrosDemo.class) throw new IllegalArgumentException(tipo.getName());
            return demo;
        });
        loader.load();
        return demo;
    }

    // snippet:controller:start
    @FXML
    private TableView<Libro> tablaLibros;

    @FXML
    private TableColumn<Libro, Integer> colId;

    @FXML
    private TableColumn<Libro, String> colTitulo;

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));

        cargar();
    }

    private void cargar() {
        try {
            tablaLibros.getItems().setAll(dao.listarTodos());
        } catch (SQLException e) {
            mostrarError("No se pudo cargar el listado.");
        }
    }
    // snippet:controller:end

    TableView<Libro> tabla() { return tablaLibros; }
    TableColumn<Libro,Integer> id() { return colId; }
    TableColumn<Libro,String> titulo() { return colTitulo; }
    void recargar() { cargar(); }

    void alternativa() {
        // snippet:alternative:start
        colTitulo.setCellValueFactory(
                celda -> new ReadOnlyStringWrapper(
                        celda.getValue().getTitulo()
                )
        );
        // snippet:alternative:end
    }
    void seleccionar() {
        idSeleccionado = -1;
        // snippet:select:start
        Libro seleccionado = tablaLibros.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarError("Selecciona un libro.");
            return;
        }

        int id = seleccionado.getId();
        // snippet:select:end
        idSeleccionado = id;
    }
    void columnas() {
        // snippet:columns:start
        colId.setCellValueFactory(
                celda -> new ReadOnlyObjectWrapper<>(
                        celda.getValue().getId()
                )
        );
        colId.setPrefWidth(80);
        colTitulo.setPrefWidth(280);
        // snippet:columns:end
    }
    void capturarSeleccion() {
        // snippet:multiple:start
        tablaLibros.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );

        List<Libro> seleccionados = new ArrayList<>(
                tablaLibros.getSelectionModel().getSelectedItems()
        );
        // snippet:multiple:end
        copiaSeleccion = seleccionados;
    }
    void escuchar() {
        // snippet:listener:start
        tablaLibros.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, anterior, actual) -> {
                    if (actual == null) {
                        txtTitulo.clear();
                    } else {
                        txtTitulo.setText(actual.getTitulo());
                    }
                });
        // snippet:listener:end
    }
    void personalizar() {
        // snippet:cell:start
        colTitulo.setCellFactory(columna -> new TableCell<Libro, String>() {

            @Override
            protected void updateItem(String titulo, boolean empty) {
                super.updateItem(titulo, empty);

                if (empty || titulo == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(titulo.toUpperCase(Locale.ROOT));
                    setGraphic(null);
                }
            }
        });
        // snippet:cell:end
    }
    private void mostrarError(String texto) { error = texto; }
}
