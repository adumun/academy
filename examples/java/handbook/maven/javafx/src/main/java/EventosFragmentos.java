import javafx.scene.control.*;

public class EventosFragmentos {
    final TextField txtTitulo = new TextField();
    final Button btnGuardar = new Button();
    final TableView<Libro> tablaLibros = new TableView<>();
    int guardados;
    private void guardarLibro() { guardados++; }
    Button crearBoton() {
        // snippet:action:start
        Button guardar = new Button("Guardar");
        guardar.setOnAction(evento -> guardarLibro());
        // snippet:action:end
        return guardar;
    }
    void escucharTitulo() {
        // snippet:listener:start
        txtTitulo.textProperty().addListener(
                (observable, anterior, actual) -> {
                    btnGuardar.setDisable(actual.isBlank());
                }
        );
        // snippet:listener:end
    }
    void escucharSeleccion() {
        // snippet:selection:start
        tablaLibros.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, anterior, actual) -> {
                    if (actual == null) {
                        txtTitulo.clear();
                    } else {
                        txtTitulo.setText(actual.getTitulo());
                    }
                });
        // snippet:selection:end
    }
}
