package navegacion;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
public class DetalleController {
    @FXML private Label lblDetalle;
    private Runnable volver;
    public void configurar(Servicio servicio, Runnable volver) {
        this.volver = volver;
        lblDetalle.setText(servicio.nombre());
    }
    @FXML private void regresar() { volver.run(); }
}
