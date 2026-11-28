import javafx.scene.control.*;
import javafx.util.StringConverter;
import java.time.LocalDate;
import java.sql.SQLException;
import java.util.List;

public class ControlesDemo {
    interface Dao { List<Libro> listarTodos() throws SQLException; }
    final ComboBox<Libro> colLibros = new ComboBox<>();
    final DatePicker fechaVencimiento = new DatePicker();
    final CheckBox chkConfirmado = new CheckBox();
    final TextField cantidadField = new TextField();
    final Label mensaje = new Label();
    private final Dao dao;
    boolean confirmadoValido;
    ControlesDemo(Dao dao) { this.dao = dao; }

    void cargarSelector() throws SQLException {
        // snippet:combo:start
        colLibros.setEditable(false);
        colLibros.getItems().setAll(dao.listarTodos());

        colLibros.setConverter(new StringConverter<Libro>() {

            @Override
            public String toString(Libro libro) {
                return libro == null ? "" : libro.getTitulo();
            }

            @Override
            public Libro fromString(String texto) {
                throw new UnsupportedOperationException(
                        "El selector no es editable"
                );
            }
        });

        Libro seleccionado = colLibros.getValue();

        if (seleccionado != null) {
            int id = seleccionado.getId();
            System.out.println(id);
        }
        // snippet:combo:end
    }
    void confirmarFecha() {
        confirmadoValido = false;
        // snippet:date:start
        LocalDate vencimiento = fechaVencimiento.getValue();
        boolean confirmado = chkConfirmado.isSelected();

        if (vencimiento == null || !confirmado) {
            mostrarError("Completa la fecha y confirma.");
            return;
        }
        // snippet:date:end
        confirmadoValido = true;
    }
    void convertirCantidad() {
        // snippet:quantity:start
        String entrada = cantidadField.getText().strip();

        try {
            int cantidad = Integer.parseInt(entrada);
            if (cantidad <= 0) {
                mensaje.setText("La cantidad debe ser positiva");
                return;
            }
            mensaje.setText("Cantidad válida: " + cantidad);
        } catch (NumberFormatException exception) {
            mensaje.setText("Ingresa un entero válido");
        }
        // snippet:quantity:end
    }
    private void mostrarError(String texto) { mensaje.setText(texto); }
}
