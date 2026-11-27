import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import java.sql.SQLException;
import java.util.List;

public class ListaFuente {
    interface Dao { List<Libro> listarTodos() throws SQLException; }
    final TableView<Libro> tablaLibros = new TableView<>();
    private final Dao dao;
    ListaFuente(Dao dao) { this.dao = dao; initialize(); }
    // snippet:source:start
    private final ObservableList<Libro> fuente =
            FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        tablaLibros.setItems(fuente);
    }

    private void cargar() throws SQLException {
        fuente.setAll(dao.listarTodos());
    }
    // snippet:source:end
    void refrescar() throws SQLException { cargar(); }
    static void alias() {
        // snippet:alias:start
        ObservableList<String> fuente = FXCollections.observableArrayList("A");
        ObservableList<String> consumidor = fuente;
        fuente.setAll("B", "C");
        System.out.println(consumidor); // [B, C]
        fuente = FXCollections.observableArrayList("D");
        System.out.println(consumidor); // [B, C]
        // snippet:alias:end
    }
}
