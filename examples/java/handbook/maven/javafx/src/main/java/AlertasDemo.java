import javafx.scene.control.*;

public class AlertasDemo {
    void confirmarEliminacion(Runnable eliminar) {
        // snippet:confirm:start
        Alert alerta = new Alert(
                Alert.AlertType.CONFIRMATION,
                "¿Eliminar el libro seleccionado?"
        );

        boolean confirmado = alerta.showAndWait()
                .filter(ButtonType.OK::equals)
                .isPresent();

        if (!confirmado) {
            return;
        }
        // snippet:confirm:end
        eliminar.run();
    }

    void informar(String mensaje) { mostrarError(mensaje); }

    // snippet:error:start
    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    // snippet:error:end
}
