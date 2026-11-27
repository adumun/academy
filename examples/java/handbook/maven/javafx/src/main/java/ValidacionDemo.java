import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.SQLException;
import java.util.function.Consumer;

public class ValidacionDemo {
    interface Dao {
        int insertar(Libro libro) throws SQLException;
        void cargar() throws SQLException;
    }
    private final Dao dao;
    private final Consumer<String> errores;
    final TextField txtTitulo = new TextField();
    final TextField txtCantidad = new TextField();
    boolean cantidadValida;
    ValidacionDemo(Dao dao, Consumer<String> errores) {
        this.dao = dao;
        this.errores = errores;
    }
    private void mostrarError(String mensaje) { errores.accept(mensaje); }
    private void cargar() throws SQLException { dao.cargar(); }
    boolean comprobarTitulo(String titulo) { return validarTitulo(titulo); }
    void ejecutarGuardado() { guardar(); }

    // snippet:title:start
    private boolean validarTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            mostrarError("El título es obligatorio.");
            return false;
        }

        if (titulo.strip().length() > 120) {
            mostrarError("El título no puede superar 120 caracteres.");
            return false;
        }

        return true;
    }
    // snippet:title:end

    // snippet:save:start
    @FXML
    private void guardar() {
        String titulo = txtTitulo.getText();

        if (!validarTitulo(titulo)) {
            return;
        }

        try {
            Libro libro = new Libro(0, titulo.strip());
            int filas = dao.insertar(libro);

            if (filas != 1) {
                mostrarError("No se confirmó la inserción.");
                return;
            }

        } catch (SQLException exception) {
            mostrarError("No se pudo confirmar el guardado del libro.");
            return;
        }

        txtTitulo.clear();
        try {
            cargar();
        } catch (SQLException exception) {
            mostrarError("Libro guardado; no se pudo actualizar el listado.");
        }
    }
    // snippet:save:end

    void validarCantidad() {
        cantidadValida = false;
        // snippet:quantity:start
        int cantidad;

        try {
            cantidad = Integer.parseInt(txtCantidad.getText().strip());
        } catch (NumberFormatException e) {
            mostrarError("Ingresa un número entero válido.");
            return;
        }

        if (cantidad <= 0) {
            mostrarError("La cantidad debe ser positiva.");
            return;
        }
        // snippet:quantity:end
        cantidadValida = true;
    }
}
