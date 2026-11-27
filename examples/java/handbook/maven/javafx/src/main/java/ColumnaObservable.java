import javafx.scene.control.TableColumn;
public class ColumnaObservable {
    final TableColumn<LibroObservable,String> colTitulo = new TableColumn<>();
    void configurar() {
        // snippet:cell:start
        colTitulo.setCellValueFactory(
                celda -> celda.getValue().tituloProperty()
        );
        // snippet:cell:end
    }
}
